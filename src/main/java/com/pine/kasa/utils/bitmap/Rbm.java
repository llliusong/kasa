package com.pine.kasa.utils.bitmap;

import org.roaringbitmap.IntConsumer;
import org.roaringbitmap.RoaringBitmap;
import org.roaringbitmap.buffer.ImmutableRoaringBitmap;
import org.roaringbitmap.buffer.MutableRoaringBitmap;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author: pine
 * @date: 2020-05-10 13:45.
 * @description:
 */
public class Rbm {

	public static void main2(String[] args) {
		// 向r1中添加1、2、3、1000四个数字
		RoaringBitmap r1 = RoaringBitmap.bitmapOf(1, 2, 3, 1000);
		// 返回第3个数字是1000
		System.out.println(r1.select(3));

		r1.add(5);

		// 返回10000的索引,是4
		System.out.println(r1.rank(1000));
		System.out.println(r1.rank(3));
		System.out.println(r1.rank(2));
		System.out.println(r1.rank(1));

		// 是否包含1000和7,true和false
		System.out.println(r1.contains(1000));
		System.out.println(r1.contains(7));

		RoaringBitmap r2 = new RoaringBitmap();
		// 向r2添加10000-12000共2000个数字
		r2.add(10000L, 12000L);

		// 将两个r1,r2进行合并,数值进行合并,合并产生新的RoaringBitmap
		RoaringBitmap r3 = RoaringBitmap.or(r1, r2);

		// r1和r2进行位运算,并将结果赋值给r1
		r1.or(r2);

		// 判断r1与r3是否相等,true
		System.out.println(r1.equals(r3));

		// 查看r1中存储了多少个值,2004
		System.out.println(r1.getLongCardinality());

		// 两种遍历方式
		for(int i : r1){
			System.out.println(i);
		}

		r1.forEach((Consumer<? super Integer>) i -> System.out.println(i.intValue()));
	}


	public static void main(String[] args) throws Exception{
		RoaringBitmap roaringBitmap = new RoaringBitmap();
		roaringBitmap.add(1L, 10L);

		// 遍历输出
		roaringBitmap.forEach((IntConsumer) System.out::println);

		// 遍历放入List中
		List<Integer> numbers = new ArrayList<>();
		roaringBitmap.forEach((IntConsumer) numbers::add);
		System.out.println(numbers);

		roaringBitmap.runOptimize();

		int size = roaringBitmap.serializedSizeInBytes();
		ByteBuffer buffer = ByteBuffer.allocate(size);
		roaringBitmap.serialize(buffer);
		// 将RoaringBitmap的数据转成字节数组,这样就可以直接存入数据库了,数据库字段类型BLOB
		byte[] bitmapData = buffer.array();

		//
		ByteBuffer buffer2 = ByteBuffer.wrap(bitmapData);

//		RoaringBitmap roaringBitmapss = new RoaringBitmap(buffer2);
		ImmutableRoaringBitmap roaringBitmapss = new ImmutableRoaringBitmap(buffer2);
		roaringBitmapss.forEach((IntConsumer) System.out::println);



		MutableRoaringBitmap rr1 = MutableRoaringBitmap.bitmapOf(1, 2, 3, 1000);
		MutableRoaringBitmap rr2 = MutableRoaringBitmap.bitmapOf( 2, 3, 1010);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		// If there were runs of consecutive values, you could
		// call rr1.runOptimize(); or rr2.runOptimize(); to improve compression
		rr1.serialize(dos);
		rr2.serialize(dos);
		dos.close();
		ByteBuffer bb = ByteBuffer.wrap(bos.toByteArray());
		ImmutableRoaringBitmap rrback1 = new ImmutableRoaringBitmap(bb);
		bb.position(bb.position() + rrback1.serializedSizeInBytes());
		ImmutableRoaringBitmap rrback2 = new ImmutableRoaringBitmap(bb);
	}
}
