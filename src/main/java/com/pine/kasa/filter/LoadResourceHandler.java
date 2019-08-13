package com.pine.kasa.filter;

import com.pine.kasa.entity.primary.Resources;
import com.pine.kasa.service.ResourcesService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Spring会先处理InitializingBean再处理init-method
 * Created by pine on 2016/10/19.
 * spring启动自动更新或加载权限资源的resource
 * 能够自动判断是否修改
 * 不支持同步删除资源 因为有角色关联
 * 避免项目启动 时间过长了 使用了懒加载来完成权限资源的更新动作过程 feiqi initialBean
 */

@Lazy
@Component()
public class LoadResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoadResourceHandler.class);

    public static final String RIGHT_CONTROLLER_PATH = "com/pine/kasa/controller";

    public static final String SUPER_ADMIN_ROLE_VALUE = "admin";

    //shiro 使用的路径是 httpRequest.getServletPath(),所以这里不加项目名
    //    public static final String PROJECT_NAME = "/community";
    public static final String PROJECT_NAME = "";

    public static final Set<String> filterController = new HashSet<String>() {{
        add("BaseController");
        add("ErrorCodeController");
        add("SyncController");
    }};

    @Resource
    private ResourcesService resourcesService;

    private TreeMap<String, Collection<String>> resourceMap = null;

    public void processLoadResource() throws Exception {

        /*Map<String, String> nameValueMap = new TreeMap<>((o1, o2)->{//modify by pine on 2016.12.21
            return String.valueOf(o1).compareTo(String.valueOf(o2));
        });*/

        Map<String, String> nameValueMap = new TreeMap<>();//modify by pine on 2016.12.21

        //查找所有属性
        List<Resources> resList = resourcesService.findAllResource();

        String controlPath = this.getClass().getClassLoader().getResource("").toString().concat(RIGHT_CONTROLLER_PATH);

        List<Map.Entry<String, String>> myListz = new MyResourceGenTools().getAllContrUrlResource(controlPath, nameValueMap).sortByTreeMapValue(nameValueMap);//modify by pine on 2016.12.21

//        List<Map.Entry<String, String>> myListz = CommonUtils.sortByTreeMapValue(nameValueMap);//modify by pine on 2016.12.21

        //TODO resource 插入数据库
        if (resList == null || resList.isEmpty()) {
            resList = new ArrayList<>();

            int v = 0;//modify by pine on 2016.12.21

            for (Map.Entry<String, String> e : myListz) {

                String value = e.getValue();//modify by pine on 2016.12.21
                boolean isTheParent = value.lastIndexOf("/") == 0;//modify by pine on 2016.12.21

                Resources p = new Resources();
                p.setName(e.getKey());
                p.setValue(PROJECT_NAME + value);

                if (isTheParent)//modify by pine on 2016.12.21
                    p.setParentId(0);//为父级节点 controller 最上面
                else
                    p.setParentId(v);

                resourcesService.insertSelective(p);

                if (isTheParent) v = p.getId();//modify by pine on 2016.12.21

                resList.add(p);//为了不查数据库 只操作该list 缓存
            }

            logger.debug("pine :初始化所有的权限资源");
        } else {
            //TODO 更新数据库
            /**
             * 三种判断 name value相同则下一个元素 name 或者 value 只有其中之一不同则更新 两个都不同则当新增的方法类型去处理
             */
            List<String> paramLis = new ArrayList<>();
            List<Map.Entry<String, String>> unhandlerLis = new ArrayList<>();

            //TODO
            resourceMapEntryHandler(myListz, resList, paramLis, unhandlerLis, (byte) 0);//处理第一次初始化的资源用 更新或者记录后加的新资源 或是真的新资源 标记 处理
            resourceMapEntryHandler(unhandlerLis, resList, paramLis, unhandlerLis, (byte) 1);//处理上次记录下来的 标记的 新资源或者是 后续追加的新资源呢 处理

            nameValueMap.clear();
            paramLis.clear();
            unhandlerLis.clear();
            logger.debug("pine :所有资源权限一更新完成");
        }

        loadResourceMap(resList);//为了不查数据库 只操作该list
    }


    private void resourceMapEntryHandler(List<Map.Entry<String, String>> myListzOrUnhandlerLis, List<Resources> resList, List<String> paramLis, List<Map.Entry<String, String>> unhandlerLis, Byte fla) {

        List<Map.Entry<String, String>> orphean = fla == 1 ? new ArrayList<>() : null;

        if (fla == 1) orphean.addAll(myListzOrUnhandlerLis);

/*        Iterator itp = myListzOrUnhandlerLis.iterator();
        while(itp.hasNext()){

            Map.Entry<String, String> e = (Map.Entry<String, String>) itp.next();*/

        for (Map.Entry<String, String> e : myListzOrUnhandlerLis) {

            String k = e.getKey();
            //这里取value的时候要加上包名，不然数据库取的时候有包名
            String v = PROJECT_NAME + e.getValue();

            Iterator it = resList.iterator();
            while (it.hasNext()) {

                Resources resource = (Resources) it.next();
                //

                /*}
                for (RightResources resource : resList) {*/

                //判断过的不再判断了
                if (paramLis.contains(resource.getName().trim())) continue;//如果有以前的历史数据库判断记录则下一个

                if (resource.getName().trim().equalsIgnoreCase(k) && resource.getValue().trim().equalsIgnoreCase(v)) {
                    if (fla == 1) orphean.remove(e);
                    paramLis.add(resource.getName().trim());//记录下已经遍历过得数据库的记录了
                    break;//下一个新元素匹配判断
                } else if (resource.getName().trim().equalsIgnoreCase(k) && !resource.getValue().trim().equalsIgnoreCase(v)) {
                    if (fla == 1) orphean.remove(e);
                    resource.setValue(v);//重新设置心智
                    resourcesService.updateSelective(resource);
                    paramLis.add(resource.getName().trim());//记录下已经遍历过得数据库的记录了
                    break;//下一个新元素匹配判断
                } else if (!resource.getName().trim().equalsIgnoreCase(k) && resource.getValue().trim().equalsIgnoreCase(v)) {
                    if (fla == 1) orphean.remove(e);
                    resource.setName(k);//重新设置心智
                    resourcesService.updateSelective(resource);
                    paramLis.add(resource.getName().trim());//记录下已经遍历过得数据库的记录了
                    break;//下一个新元素匹配判断
                } else {///添加追加的资源.

                    //新增的资源 跳过了判断
                    if (fla == 0) {//第一次循环只做记录
                        unhandlerLis.add(e);
                        break;
                    }
                }
            }
        }

        if (fla == 1) {///处理最后遗留的 需要新添加的资源类 处理

/*            itp = myListzOrUnhandlerLis.iterator();
            while (itp.hasNext()) {
            Map.Entry<String, String> e = (Map.Entry<String, String>) itp.next();*/


            for (Map.Entry<String, String> e : orphean) {
               /* for(Map.Entry<String, String> o : orphean){
                    if(e.getKey() != o.getKey()) {

                    }
                }*/


                String k = e.getKey();
                String v = e.getValue();


                //TODO 删除老的数据值
                Resources rtrs = new Resources();
                rtrs.setName(k);
                rtrs.setValue(PROJECT_NAME + v);

                int z = v.lastIndexOf("/");//modify by pine on 2016.12.21

                if (z != 0) {//modify by pine on 2016.12.21
                    String parent = v.substring(0, z);
                        /*MyRestrictions mrs = MyRestrictions.getMyRestrctions().singleParam("value", parent);
                        List<RightResources> rs = resourceService.selectByGaze(mrs);
                        rtrs.setPid(rs.get(0).getId());*/

                    Iterator itr = resList.iterator();//重新获取到
                    while (itr.hasNext()) {
                        Resources l = (Resources) itr.next();
                        if (l.getValue().equals(parent)) {
                            rtrs.setParentId(l.getId());
                        }
                    }
                } else {
                    rtrs.setParentId(0);
                }

                resourcesService.insertSelective(rtrs);

                resList.add(rtrs);//为了不查数据库 只操作该list
            }

            orphean.clear();
        }

    }


    public synchronized TreeMap<String, Collection<String>> getResourceMap() {//modify by pine on 2016.12.21 一直重复执行看两遍 终于找到了原因
        if (resourceMap == null) {///第一次加载
            try {
                processLoadResource();
            } catch (Exception e) {
                logger.error("pine :初始化权限资源的时候出现了错误异常", e);
            }
        }
        return resourceMap;
    }

    public synchronized Map<String, Collection<String>> loadResourceMap(List<Resources> resourcesList) {///////添加同步 主要是 用户在拦截过程判断时调用此方法的同时 也有用户在改权限信息reload 避免高并发同时对这个treemap操作 不然就有很多重复数据了

        if (resourcesList == null || resourcesList.isEmpty()) return null;

        resourceMap = new TreeMap<>((o1, o2) -> {
            return String.valueOf(o2).compareTo(String.valueOf(o1));
        });

        for (Resources resource : resourcesList) {
            String resourceValue = resource.getValue();

            Collection<String> atts = new ArrayList<>();
            atts.add(resourceValue);
//
//            if (resource.getRoleSet() != null && !resource.getRoleSet().isEmpty()) {
//
//                atts = new ArrayList<>();
//
//                for (RoleEntity role : resource.getRoleSet()) {
//                    atts.add(role.getValue());
//                }
//            }

            resourceMap.put(resourceValue, atts);
        }

        logger.debug("pine :加载或重载resource treemap 完成");
        return resourceMap;
    }


    public Collection<String> makeBareRole() {
        Collection<String> atts = new ArrayList<>(1);
        atts.add(SUPER_ADMIN_ROLE_VALUE);///TODO
        return atts;
    }

    public synchronized Map<String, Collection<String>> reloadResourceMap() {

        return loadResourceMap(resourcesService.findAllResource());
    }


    public class MyResourceGenTools {

        public MyResourceGenTools getAllContrUrlResource(String path, Map<String, String> paramMaps) throws ClassNotFoundException {

            if (StringUtils.isBlank(path)) return null;

            if (path.contains("file:")) path = path.replace("file:", "");

            String p = StringUtils.substringAfter(path, "classes/");//取得指定字符串后的字符串

            for (String contrName : getAllFileName(path, null)) {
                getContrlUrlResource(p.concat("/" + contrName), paramMaps);
            }

            return this;
        }


        /**
         * 根据文件的名字 填充到map的 name - value
         * 资源权限使用
         *
         * @param resourceName
         * @param paramMaps
         * @throws ClassNotFoundException
         */
        public void getContrlUrlResource(String resourceName, Map<String, String> paramMaps) throws ClassNotFoundException {
            if (StringUtils.isBlank(resourceName)) return;

            for (String string : filterController) {
                if (resourceName.contains(string)) return;
            }

            if (resourceName.contains(".class")) resourceName = resourceName.replace(".class", "");

            //内部类会使class文件加$1
            if (resourceName.contains("$1")) resourceName = resourceName.replace("$1", "");

            if (resourceName.contains("/")) resourceName = resourceName.replace("/", ".");

            Class<?> c = Class.forName(resourceName);

            RequestMapping[] k = c.getAnnotationsByType(RequestMapping.class);


            String bigPath = "";
            String bigPathName = "";

            int i = 0, j = 0;

            if (k != null && k.length != 0) {
                for (; i < k.length; i++) {

                    String[] l = k[i].value();
                    bigPathName = k[i].name();

                    if (l != null && l.length != 0) {
                        for (; j < l.length; j++) {

                            //String p = StringUtils.isBlank(k[i].name()) ? l[j].concat("/**") : k[i].name();

                            //paramMaps.put(p, l[j].concat("/**"));
                            bigPath = l[j];//TODO 取的最后一个前罪名
                        }
                    }
                }
            }

            //modify by pine on 2016.12.20
            //存入父级节点项

            if (StringUtils.isBlank(bigPath))
                throw new RuntimeException(c.getName() + " 最上面那个@RequestMapping的value值未写!");

            bigPathName = StringUtils.isBlank(bigPathName) ? bigPath : bigPathName;

            paramMaps.put(bigPathName, bigPath);
            //

            Method[] methods = c.getMethods();

            for (Method m : methods) {

                if (!m.isAccessible()) m.setAccessible(true);

                PostMapping[] s = m.getAnnotationsByType(PostMapping.class);
                //  2018/9/11 controller 里方法必须加public，否则获取不到

                i = 0;
                j = 0;
                if (s != null && s.length != 0) {
                    for (; i < s.length; i++) {

                        String[] l = s[i].value();
                        if (l != null && l.length != 0) {
                            for (; j < l.length; j++) {

                                String p = StringUtils.isBlank(s[i].name()) ? bigPath.concat(l[j]) : s[i].name();

                                paramMaps.put(p, bigPath.concat(l[j]));
                            }
                        }
                    }
                }
            }
        }

        /**
         * 获取方法的路径和名称
         */
        private void getMethodPathAndPathName( Method m){
            // 目前只兼容这三个
            if (m.isAnnotationPresent(RequestMapping.class)){
                RequestMapping[] s = m.getAnnotationsByType(RequestMapping.class);

            }else if (m.isAnnotationPresent(PostMapping.class)){
                PostMapping[] s = m.getAnnotationsByType(PostMapping.class);

            }else if (m.isAnnotationPresent(GetMapping.class)){
                GetMapping[] s = m.getAnnotationsByType(GetMapping.class);
            }
        }



        /**
         * 循环递归查询出 某文件下的所有子文件名称
         *
         * @param path
         * @param fileNameList
         */
        public List<String> getAllFileName(String path, ArrayList<String> fileNameList) {

            if (fileNameList == null) fileNameList = new ArrayList<>();

            File file = new File(path);

            File[] files = file.listFiles();

            String[] names = file.list();

            if (names != null) fileNameList.addAll(Arrays.asList(names));

            if (files != null && files.length != 0) {
                for (File a : files) {
                    if (a.isDirectory()) getAllFileName(a.getAbsolutePath(), fileNameList);
                }
            }

            return fileNameList;

        }

        public List<Map.Entry<String, String>> sortByTreeMapValue(Map<String, String> treeMap) {

            List<Map.Entry<String, String>> list = new ArrayList<>(treeMap.entrySet());

            Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
                //升序排序
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });

            return list;
        }

    }

    //TODO reload
}
