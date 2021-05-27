package org.supportmeinc.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class CardMap<S, U> extends HashMap<S, U> {
    private final Object lock = new Object();

    public S replaceOnValue(U value, S newKey) {
        S oldKey = null;

        for (Entry<S, U> entry : entrySet()) {
            System.out.println("Key for entry : " + entry.getKey());
            if (entry.getValue().equals(value)) {
                oldKey = entry.getKey();
            }
        }
        if (oldKey != null) {
            remove(oldKey);
            put(newKey, value);
        }

        return oldKey;
    }
}
