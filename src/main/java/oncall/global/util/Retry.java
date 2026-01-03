package oncall.global.util;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public final class Retry {

    private Retry() {}

    public static <T> T retry(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                throw e;
            }
        }
    }
}

