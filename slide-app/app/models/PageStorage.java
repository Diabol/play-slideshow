package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageStorage {

    private static PageStorage instance;
    private static Set<Page> pages = new HashSet<Page>();;

    static {
        instance = new PageStorage();
    }

    private PageStorage() {
    }

    public synchronized static PageStorage getInstance() {
        return instance;
    }

    public synchronized List<Page> getPages() {
        List<Page> result = new ArrayList<Page>();
        result.addAll(pages);
        return result;
    }

    public synchronized void addPage(Page page) {
        pages.add(page);
    }

    public synchronized void removePage(String name) {
        for (Page saved : pages) {
            if (saved.getName().equals(name)) {
                pages.remove(saved);
            }
        }
    }
}
