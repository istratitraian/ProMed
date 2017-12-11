/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers.pagenav;

import java.util.Objects;

/**
 *
 * @author I.T.W764
 */
public class PageNavElement {

    private Integer element = null;
    private boolean pageIndex = false;
    private String href;

    public boolean isEqPageIndex() {
        return pageIndex;
    }

    public boolean isNull() {
        return element == null;
    }

    public Integer getElement() {
        return element;
    }

    public String getHref() {
        return href + element;
    }

    public PageNavElement() {
    }

    public PageNavElement(int el, Integer pageIndex, String href) {
        this.href = href;
        this.element = el;
        this.pageIndex = Objects.equals(element, pageIndex);
    }
}
