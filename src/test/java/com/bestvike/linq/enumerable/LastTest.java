package com.bestvike.linq.enumerable;

import com.bestvike.linq.IEnumerable;
import com.bestvike.linq.Linq;
import com.bestvike.linq.exception.InvalidOperationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by 许崇雷 on 2018-05-10.
 */
public class LastTest extends EnumerableTest {
    @Test
    public void testLast() {
        final IEnumerable<String> enumerable = Linq.asEnumerable(Arrays.asList("jimi", "mitch"));
        Assert.assertEquals("mitch", enumerable.last());

        final IEnumerable emptyEnumerable = Linq.asEnumerable(Collections.emptyList());
        try {
            emptyEnumerable.last();
            Assert.fail("should not run at here");
        } catch (InvalidOperationException ignored) {
        }

        final IEnumerable<String> enumerable2 = Linq.asEnumerable(Collections.unmodifiableCollection(Arrays.asList("jimi", "noel", "mitch")));
        Assert.assertEquals("mitch", enumerable2.last());
    }

    @Test
    public void testLastWithPredicate() {
        final IEnumerable<String> enumerable = Linq.asEnumerable(Arrays.asList("jimi", "mitch", "ming"));
        Assert.assertEquals("mitch", enumerable.last(x -> x.startsWith("mit")));
        try {
            enumerable.last(x -> false);
            Assert.fail();
        } catch (InvalidOperationException ignored) {
        }

        final IEnumerable<String> emptyEnumerable = Linq.asEnumerable(Collections.emptyList());
        try {
            emptyEnumerable.last(x -> {
                Assert.fail("should not run at here");
                return false;
            });
            Assert.fail();
        } catch (InvalidOperationException ignored) {
        }
    }

    @Test
    public void testLastOrDefault() {
        final IEnumerable<String> enumerable = Linq.asEnumerable(Arrays.asList("jimi", "mitch"));
        Assert.assertEquals("mitch", enumerable.lastOrDefault());

        final IEnumerable emptyEnumerable = Linq.asEnumerable(Collections.emptyList());
        Assert.assertNull(emptyEnumerable.lastOrDefault());
    }

    @Test
    public void testLastOrDefaultWithPredicate() {
        final IEnumerable<String> enumerable = Linq.asEnumerable(Arrays.asList("jimi", "mitch", "ming"));
        Assert.assertEquals("mitch", enumerable.lastOrDefault(x -> x.startsWith("mit")));
        Assert.assertNull(enumerable.lastOrDefault(x -> false));
        final IEnumerable<String> emptyEnumerable = Linq.asEnumerable(Collections.emptyList());
        Assert.assertNull(emptyEnumerable.lastOrDefault(x -> {
            Assert.fail("should not run at here");
            return false;
        }));
    }
}
