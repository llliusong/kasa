# kasa


const list =  ['1795936469911736320', '1795936466786979840', '1795936464735965184', '1795936453428121600', '1795936449300926464']

const token = localStorage.getItem('token')
const userId = localStorage.getItem('uid')

for (const id of list) {
  const url = `https://enterpriseapi.coolcollege.cn/v2/1067985194675474450/users/${userId}/studies/1796221147356991488/courses/1795936485908811776/resources/${id}/save_progress?access_token=${token}`
  fetch(url, {"credentials":"omit","headers":{"accept":"application/json, text/plain, */*","accept-language":"zh-CN,zh;q=0.9","content-type":"application/json;charset=UTF-8"},"referrer":"https://learning.coolcollege.cn/?eid=1067985194675474450","referrerPolicy":"no-referrer-when-downgrade","body":"{\"progress\":100,\"recent_start\":2,\"tempTime\":1596695165000,\"access_token\":\"d1732344cf74667aafe50fd68b0fdb01\"}","method":"POST","mode":"cors"});
}