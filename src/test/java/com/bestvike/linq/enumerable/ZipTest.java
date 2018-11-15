package com.bestvike.linq.enumerable;

import com.bestvike.linq.IEnumerable;
import com.bestvike.linq.Linq;
import com.bestvike.linq.exception.ArgumentNullException;
import com.bestvike.linq.extend.ThrowsOnMatchEnumerable;
import com.bestvike.tuple.Tuple;
import com.bestvike.tuple.Tuple2;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by 许崇雷 on 2018-05-10.
 */
public class ZipTest extends EnumerableTest {
    @Test
    public void Zip2_ImplicitTypeParameters() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 2, 3});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 5, 9});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(2, 5), Tuple.create(3, 9)});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_FirstIsNull() {
        IEnumerable<Integer> first = null;
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 5, 9});
        assertThrows(NullPointerException.class, () -> first.zip(second));
    }

    @Test
    public void Zip2_SecondIsNull() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 2, 3});
        IEnumerable<Integer> second = null;
        assertThrows(ArgumentNullException.class, () -> first.zip(second));
    }

    @Test
    public void Zip2_ExceptionThrownFromFirstsEnumerator() {
        IEnumerable<Integer> first = new ThrowsOnMatchEnumerable<>(Linq.asEnumerable(new int[]{1, 3, 3}), 2);
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 4, 6});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(3, 4), Tuple.create(3, 6)});
        assertEquals(expected, first.zip(second));

        first = new ThrowsOnMatchEnumerable<>(Linq.asEnumerable(new int[]{1, 2, 3}), 2);
        IEnumerable<Tuple2<Integer, Integer>> zip = first.zip(second);
        assertThrows(Exception.class, zip::toList);
    }

    @Test
    public void Zip2_ExceptionThrownFromSecondsEnumerator() {
        ThrowsOnMatchEnumerable<Integer> second = new ThrowsOnMatchEnumerable<>(Linq.asEnumerable(new int[]{1, 3, 3}), 2);
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{2, 4, 6});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(2, 1), Tuple.create(4, 3), Tuple.create(6, 3)});
        assertEquals(expected, first.zip(second));

        second = new ThrowsOnMatchEnumerable<>(Linq.asEnumerable(new int[]{1, 2, 3}), 2);
        IEnumerable<Tuple2<Integer, Integer>> zip = first.zip(second);
        assertThrows(Exception.class, zip::toList);
    }

    @Test
    public void Zip2_FirstAndSecondEmpty() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_FirstEmptySecondSingle() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_FirstEmptySecondMany() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 4, 8});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_SecondEmptyFirstSingle() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_SecondEmptyFirstMany() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 2, 3});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_FirstAndSecondSingle() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2)});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_FirstAndSecondEqualSize() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 2, 3});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 3, 4});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(2, 3), Tuple.create(3, 4)});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_SecondOneMoreThanFirst() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 2});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 4, 8});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(2, 4)});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_SecondManyMoreThanFirst() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 2});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 4, 8, 16});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(2, 4)});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_FirstOneMoreThanSecond() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 2, 3});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 4});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(2, 4)});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_FirstManyMoreThanSecond() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 2, 3, 4});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 4});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(2, 4)});
        assertEquals(expected, first.zip(second));
    }

    @Test
    public void Zip2_RunOnce() {
        IEnumerable<Integer> first = Linq.asEnumerable(1, null, 3);
        IEnumerable<Integer> second = Linq.asEnumerable(2, 4, 6, 8);
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> expected = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(null, 4), Tuple.create(3, 6)});
        assertEquals(expected, first.runOnce().zip(second.runOnce()));
    }

    @Test
    public void Zip2_NestedTuple() {
        IEnumerable<Integer> first = Linq.asEnumerable(new int[]{1, 3, 5});
        IEnumerable<Integer> second = Linq.asEnumerable(new int[]{2, 4, 6});
        //noinspection unchecked
        IEnumerable<Tuple2<Integer, Integer>> third = Linq.asEnumerable((Tuple2<Integer, Integer>[]) new Tuple2[]{Tuple.create(1, 2), Tuple.create(3, 4), Tuple.create(5, 6)});
        assertEquals(third, first.zip(second));

        IEnumerable<String> fourth = Linq.asEnumerable("one", "two", "three");
        //noinspection unchecked
        IEnumerable<Tuple2<Tuple2<Integer, Integer>, String>> expected = Linq.asEnumerable((Tuple2<Tuple2<Integer, Integer>, String>[]) new Tuple2[]{Tuple.create(Tuple.create(1, 2), "one"), Tuple.create(Tuple.create(3, 4), "two"), Tuple.create(Tuple.create(5, 6), "three")});
        assertEquals(expected, third.zip(fourth));
    }

    @Test
    public void Zip2_TupleNames() {
        final Tuple2<Integer, Integer> t = Linq.asEnumerable(1, 2, 3).zip(Linq.asEnumerable(2, 4, 6)).first();
        Assert.assertEquals(1, (int) t.getItem1());
        Assert.assertEquals(2, (int) t.getItem2());
    }

    @Test
    public void testZip() {
        final IEnumerable<String> e1 = Linq.asEnumerable(Arrays.asList("a", "b", "c"));
        final IEnumerable<String> e2 = Linq.asEnumerable(Arrays.asList("1", "2", "3"));

        final IEnumerable<String> zipped = e1.zip(e2, (v0, v1) -> v0 + v1);
        Assert.assertEquals(3, zipped.count());
        for (int i = 0; i < 3; i++)
            Assert.assertEquals("" + (char) ('a' + i) + (char) ('1' + i), zipped.elementAt(i));
    }

    @Test
    public void testZipLengthNotMatch() {
        final IEnumerable<String> e1 = Linq.asEnumerable(Arrays.asList("a", "b"));
        final IEnumerable<String> e2 = Linq.asEnumerable(Arrays.asList("1", "2", "3"));


        final IEnumerable<String> zipped1 = e1.zip(e2, (v0, v1) -> v0 + v1);
        Assert.assertEquals(2, zipped1.count());
        for (int i = 0; i < 2; i++)
            Assert.assertEquals("" + (char) ('a' + i) + (char) ('1' + i), zipped1.elementAt(i));

        final IEnumerable<String> zipped2 = e2.zip(e1, (v0, v1) -> v0 + v1);
        Assert.assertEquals(2, zipped2.count());
        for (int i = 0; i < 2; i++)
            Assert.assertEquals("" + (char) ('1' + i) + (char) ('a' + i), zipped2.elementAt(i));
    }
}
