package com.bestvike.linq.enumerable;

import com.bestvike.collections.generic.Array;
import com.bestvike.function.Func1;
import com.bestvike.linq.IEnumerable;
import com.bestvike.linq.Linq;
import com.bestvike.linq.exception.ArgumentNullException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by 许崇雷 on 2018-05-11.
 */
public class AllTest extends EnumerableTest {
    @Test
    public void SameResultsRepeatCallsIntQuery() {
        IEnumerable<Integer> q = Linq.asEnumerable(9999, 0, 888, -1, 66, -777, 1, 2, -12345)
                .where(x -> x > Integer.MIN_VALUE);

        Func1<Integer, Boolean> predicate = EnumerableTest::IsEven;
        Assert.assertEquals(q.all(predicate), q.all(predicate));
    }

    @Test
    public void SameResultsRepeatCallsStringQuery() {
        IEnumerable<String> q = Linq.asEnumerable("!@#$%^", "C", "AAA", "", "Calling Twice", "SoS", Empty);

        Func1<String, Boolean> predicate = EnumerableTest::IsNullOrEmpty;
        Assert.assertEquals(q.all(predicate), q.all(predicate));
    }

    @Test
    public void All() {
        Func1<Integer, Boolean> isEvenFunc = EnumerableTest::IsEven;
        this.AllCore(Linq.singleton(0), isEvenFunc, true);
        this.AllCore(Linq.singleton(3), isEvenFunc, false);
        this.AllCore(Linq.singleton(4), isEvenFunc, true);
        this.AllCore(Linq.singleton(3), isEvenFunc, false);

        this.AllCore(Linq.asEnumerable(4, 8, 3, 5, 10, 20, 12), isEvenFunc, false);
        this.AllCore(Linq.asEnumerable(4, 2, 10, 12, 8, 6, 3), isEvenFunc, false);
        this.AllCore(Linq.asEnumerable(4, 2, 10, 12, 8, 6, 14), isEvenFunc, true);

        Array<Integer> range = Linq.range(1, 10).toArray();
        this.AllCore(range, i -> i > 0, true);
        for (int j = 1; j <= 10; j++) {
            final int k = j;
            this.AllCore(range, i -> i > k, false);
        }
    }

    private void AllCore(IEnumerable<Integer> source, Func1<Integer, Boolean> predicate, boolean expected) {
        Assert.assertEquals(expected, source.all(predicate));
    }

    @Test
    public void AllRunOnce() {
        Func1<Integer, Boolean> isEvenFunc = EnumerableTest::IsEven;
        this.AllRunOnceCore(Linq.singleton(0), isEvenFunc, true);
        this.AllRunOnceCore(Linq.singleton(3), isEvenFunc, false);
        this.AllRunOnceCore(Linq.singleton(4), isEvenFunc, true);
        this.AllRunOnceCore(Linq.singleton(3), isEvenFunc, false);

        this.AllRunOnceCore(Linq.asEnumerable(4, 8, 3, 5, 10, 20, 12), isEvenFunc, false);
        this.AllRunOnceCore(Linq.asEnumerable(4, 2, 10, 12, 8, 6, 3), isEvenFunc, false);
        this.AllRunOnceCore(Linq.asEnumerable(4, 2, 10, 12, 8, 6, 14), isEvenFunc, true);

        Array<Integer> range = Linq.range(1, 10).toArray();
        this.AllRunOnceCore(range.runOnce(), i -> i > 0, true);
        for (int j = 1; j <= 10; j++) {
            final int k = j;
            this.AllRunOnceCore(range, i -> i > k, false);
        }
    }

    private void AllRunOnceCore(IEnumerable<Integer> source, Func1<Integer, Boolean> predicate, boolean expected) {
        Assert.assertEquals(expected, source.runOnce().all(predicate));
    }

    @Test
    public void NullSource_ThrowsArgumentNullException() {
        assertThrows(NullPointerException.class, () -> ((IEnumerable<Integer>) null).all(i -> i != 0));
    }

    @Test
    public void NullPredicate_ThrowsArgumentNullException() {
        Func1<Integer, Boolean> predicate = null;
        assertThrows(ArgumentNullException.class, () -> Linq.range(0, 3).all(predicate));
    }
}
