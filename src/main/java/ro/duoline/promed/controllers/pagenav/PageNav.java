/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers.pagenav;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

/**
 *
 * @author I.T.W764
 */
public class PageNav {

    public final int ITEMS_PER_PAGE_COUNT;
    public final String HREF;

    public PageNav(String href) {
        this(10, href);
    }

    public PageNav(int itemsPerPage, String href) {
        this.ITEMS_PER_PAGE_COUNT = itemsPerPage;
        this.HREF = href;
    }

    public List<?> buildPageNav(Model model, int pageIndex, List<?> list) {

        int pageCount;
        if (list.size() % ITEMS_PER_PAGE_COUNT != 0) {
            pageCount = list.size() / ITEMS_PER_PAGE_COUNT + 1;
        } else {
            pageCount = list.size() / ITEMS_PER_PAGE_COUNT;
        }


        final int PAGE_NAV_SIZE = pageCount > 7 ? 6 : pageCount;

        List<PageNavElement> pageElementList = new ArrayList<>(PAGE_NAV_SIZE);
        pageElementList.add(new PageNavElement(1, pageIndex, HREF));

        if (PAGE_NAV_SIZE > 1) {

            int begin;
            int end;

            boolean isPageIndexPl3LtPcMin1 = pageIndex + 3 < pageCount;

            if (pageIndex - 4 > 0) {

                pageElementList.add(new PageNavElement());

                if (isPageIndexPl3LtPcMin1) {
                    begin = pageIndex - 1;
                    end = pageIndex + 2;
                } else {
                    begin = pageCount - 4;
                    end = pageCount - 1;
                }

            } else {
                begin = 3;
                end = PAGE_NAV_SIZE;
                pageElementList.add(new PageNavElement(2, pageIndex, HREF));
            }

            if (PAGE_NAV_SIZE > 2) {
                for (; begin < end; begin++) {
                    PageNavElement pageNavElement = new PageNavElement(begin, pageIndex, HREF);
                    pageElementList.add(pageNavElement);
                }

                if (pageCount > 5) {
                    if (isPageIndexPl3LtPcMin1) {
                        pageElementList.add(new PageNavElement());
                    } else {
                        pageElementList.add(new PageNavElement(pageCount - 1, pageIndex, HREF));
                    }
                    if (pageCount > 6) {
                        pageElementList.add(new PageNavElement(pageCount, pageIndex, HREF));
                    }
                }
            }
        }

        model.addAttribute("pageElementList", pageElementList);
        
        int begin = 0;

        if(pageIndex > 1) {
            begin = (pageIndex * ITEMS_PER_PAGE_COUNT) - ITEMS_PER_PAGE_COUNT;
        }

        int end = begin + ITEMS_PER_PAGE_COUNT;

        if (end > list.size()) {
            end = list.size();
        }

        System.out.println("PageNav.buildPageNav() pageCount = " + pageCount + ", list.size() = " + list.size());
        System.out.println("PageNav.buildPageNav() begin = " + begin + ", end = " + end);

        return list.subList(begin, end);
    }

}
