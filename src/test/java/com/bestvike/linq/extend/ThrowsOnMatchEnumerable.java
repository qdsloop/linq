package com.bestvike.linq.extend;

import com.bestvike.linq.IEnumerable;
import com.bestvike.linq.IEnumerator;
import com.bestvike.linq.enumerable.AbstractEnumerator;

import java.util.Objects;

/**
 * Created by 许崇雷 on 2018-11-14.
 */
public final class ThrowsOnMatchEnumerable<T> implements IEnumerable<T> {
    private final IEnumerable<T> source;
    private final T thrownOn;

    public ThrowsOnMatchEnumerable(IEnumerable<T> source, T thrownOn) {
        this.source = source;
        this.thrownOn = thrownOn;
    }

    @Override
    public IEnumerator<T> enumerator() {
        return new ThrowsOnMatchEnumerator<>(this.source, this.thrownOn);
    }


    private static class ThrowsOnMatchEnumerator<T> extends AbstractEnumerator<T> {
        private final IEnumerable<T> source;
        private final T thrownOn;
        private IEnumerator<T> enumerator;

        private ThrowsOnMatchEnumerator(IEnumerable<T> source, T thrownOn) {
            this.source = source;
            this.thrownOn = thrownOn;
        }

        @Override
        public boolean moveNext() {
            switch (this.state) {
                case 0:
                    this.enumerator = this.source.enumerator();
                    this.state = 1;
                case 1:
                    if (this.enumerator.moveNext()) {
                        T item = this.enumerator.current();
                        if (Objects.equals(item, this.thrownOn))
                            throw new RuntimeException();
                        this.current = item;
                        return true;
                    }
                    this.close();
                    return false;
                default:
                    return false;
            }
        }

        @Override
        public void close() {
            if (this.enumerator != null) {
                this.enumerator.close();
                this.enumerator = null;
            }
            super.close();
        }
    }
}
