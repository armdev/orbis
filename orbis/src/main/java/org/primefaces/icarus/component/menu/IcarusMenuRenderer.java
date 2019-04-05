package org.primefaces.icarus.component.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.component.api.AjaxSource;
import org.primefaces.component.api.UIOutcomeTarget;
import org.primefaces.component.menu.AbstractMenu;
import org.primefaces.component.menu.BaseMenuRenderer;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.Separator;
import org.primefaces.model.menu.Submenu;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.WidgetBuilder;

public class IcarusMenuRenderer extends BaseMenuRenderer {

    @Override
    @SuppressWarnings("unchecked")
    protected void encodeMarkup(FacesContext context, AbstractMenu abstractMenu) throws IOException {
        try {
            IcarusMenu menu = (IcarusMenu) abstractMenu;
            ResponseWriter writer = context.getResponseWriter();
            String style = menu.getStyle();
            String styleClass = menu.getStyleClass();
            String id = "layout-menu";
            styleClass = (styleClass == null) ? "sidebar-nav-container" : "sidebar-nav-container " + styleClass;

            writer.startElement("ul", menu);
            writer.writeAttribute("id", id, "id");
            writer.writeAttribute("class", styleClass, "styleClass");
            if (style != null) {
                writer.writeAttribute("style", style, "style");
            }
            if (menu.getElementsCount() > 0) {
                encodeElements(context, menu, menu.getElements());
            }
            writer.endElement("ul");
        } catch (IOException e) {

        }
    }

    protected void encodeElements(FacesContext context, AbstractMenu menu, List<MenuElement> elements) throws IOException {
        try {
            int size = elements.size();
            for (int i = 0; i < size; i++) {
                encodeElement(context, menu, elements.get(i));
            }
        } catch (IOException e) {

        }
    }

    protected void encodeElement(FacesContext context, AbstractMenu menu, MenuElement element) throws IOException {
        ResponseWriter writer = context.getResponseWriter();

        if (element.isRendered()) {
            if (element instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) element;
                String menuItemClientId = (menuItem instanceof UIComponent) ? menuItem.getClientId() : menu.getClientId(context) + "_" + menuItem.getClientId();
                String containerStyle = menuItem.getContainerStyle();
                String containerStyleClass = menuItem.getContainerStyleClass();
                boolean isParent = (menuItem instanceof DefaultMenuItem) ? isParentRootMenu(menuItem.getId()) : isParentRootMenu((UIComponent) menuItem);
                String defaultStyleClass = isParent ? "sidebar-menuitem" : "sidebar-submenuitem";

                containerStyleClass = (containerStyleClass == null) ? defaultStyleClass : defaultStyleClass + " " + containerStyleClass;

                writer.startElement("li", null);
                writer.writeAttribute("id", menuItemClientId, null);
                writer.writeAttribute("role", "menuitem", null);
                writer.writeAttribute("class", containerStyleClass, null);

                if (containerStyle != null) {
                    writer.writeAttribute("style", containerStyle, null);
                }

                encodeMenuItem(context, menu, menuItem);

                writer.endElement("li");
            } else if (element instanceof Submenu) {
                Submenu submenu = (Submenu) element;
                String submenuClientId = (submenu instanceof UIComponent) ? ((UIComponent) submenu).getClientId() : menu.getClientId(context) + "_" + submenu.getId();
                String style = submenu.getStyle();
                String styleClass = submenu.getStyleClass();
                boolean isParent = (submenu instanceof DefaultSubMenu) ? isParentRootMenu(submenu.getId()) : isParentRootMenu((UIComponent) submenu);
                String defaultStyleClass = isParent ? "sidebar-menuitem" : "sidebar-submenuitem";
                styleClass = (styleClass == null) ? defaultStyleClass : defaultStyleClass + " " + styleClass;

                writer.startElement("li", null);
                writer.writeAttribute("id", submenuClientId, null);
                writer.writeAttribute("role", "menuitem", null);
                writer.writeAttribute("class", styleClass, null);

                if (style != null) {
                    writer.writeAttribute("style", style, null);
                }

                encodeSubmenu(context, menu, submenu);

                writer.endElement("li");
            } else if (element instanceof Separator) {
                encodeSeparator(context, (Separator) element);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void encodeSubmenu(FacesContext context, AbstractMenu menu, Submenu submenu) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String icon = submenu.getIcon();
        String label = submenu.getLabel();
        int childrenElementsCount = submenu.getElementsCount();

        writer.startElement("a", null);
        writer.writeAttribute("href", "#", null);
        writer.writeAttribute("class", "menuLink", null);

        if (icon != null) {
            writer.startElement("i", null);
            writer.writeAttribute("class", icon, null);
            writer.endElement("i");
        }

        if (label != null) {
            boolean isRootMenu = (submenu instanceof DefaultSubMenu) ? isParentRootMenu(submenu.getId()) : isParentRootMenu((UIComponent) submenu);
            if (isRootMenu) {
                writer.startElement("span", null);
                writer.writeAttribute("class", "menu-text-group", null);
            }

            writer.startElement("span", null);
            writer.writeAttribute("class", "menu-text", null);
            writer.writeText(label, null);
            writer.endElement("span");

            encodeToggleIcon(context, submenu, childrenElementsCount);

            if (isRootMenu) {
                writer.endElement("span");
            }
        }

        writer.endElement("a");

        //submenus and menuitems
        if (childrenElementsCount > 0) {
            writer.startElement("ul", null);
            writer.writeAttribute("role", "menu", null);
            writer.writeAttribute("class", "sidebar-submenu-container", null);
            encodeElements(context, menu, submenu.getElements());
            writer.endElement("ul");
        }
    }

    protected void encodeToggleIcon(FacesContext context, Submenu submenu, int childrenElementsCount) throws IOException {
        if (childrenElementsCount > 0) {
            ResponseWriter writer = context.getResponseWriter();

            writer.startElement("i", null);
            writer.writeAttribute("class", "fa fa-angle-right", null);
            writer.endElement("i");
        }
    }

    protected boolean isParentRootMenu(UIComponent component) {
        UIComponent parentComponent = component.getParent();
        return parentComponent instanceof IcarusMenu;
    }

    protected boolean isParentRootMenu(String elementId) {
        if (elementId.trim().length() == 1) {
            return true;
        }

        return false;
    }

    @Override
    protected void encodeSeparator(FacesContext context, Separator separator) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String style = separator.getStyle();
        String styleClass = separator.getStyleClass();
        styleClass = styleClass == null ? "Separator" : "Separator " + styleClass;

        //title
        writer.startElement("li", null);
        writer.writeAttribute("class", styleClass, null);
        if (style != null) {
            writer.writeAttribute("style", style, null);
        }

        writer.endElement("li");
    }

    @Override
    protected void encodeMenuItem(FacesContext context, AbstractMenu menu, MenuItem menuitem) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String title = menuitem.getTitle();
        boolean disabled = menuitem.isDisabled();
        String style = menuitem.getStyle();
        String defaultStyleClass = "menuLink";
        String styleClass = menuitem.getStyleClass();
        styleClass = (styleClass) == null ? defaultStyleClass : defaultStyleClass + " " + styleClass;

        writer.startElement("a", null);
        if (title != null) {
            writer.writeAttribute("title", title, null);
        }
        if (style != null) {
            writer.writeAttribute("style", style, null);
        }

        writer.writeAttribute("class", styleClass, null);

        if (disabled) {
            writer.writeAttribute("href", "#", null);
            writer.writeAttribute("onclick", "return false;", null);
        } else {
            String onclick = menuitem.getOnclick();

            //GET
            if (menuitem.getUrl() != null || menuitem.getOutcome() != null) {
                String targetURL = getTargetURL(context, (UIOutcomeTarget) menuitem);
                writer.writeAttribute("href", targetURL, null);

                if (menuitem.getTarget() != null) {
                    writer.writeAttribute("target", menuitem.getTarget(), null);
                }
            } //POST
            else {
                writer.writeAttribute("href", "#", null);

                UIComponent form = ComponentUtils.findParentForm(context, menu);
                if (form == null) {
                    throw new FacesException("MenuItem must be inside a form element");
                }

                String command;
                if (menuitem.isDynamic()) {
                    String menuClientId = menu.getClientId(context);
                    Map<String, List<String>> params = menuitem.getParams();
                    if (params == null) {
                        params = new LinkedHashMap<>();
                    }
                    List<String> idParams = new ArrayList<>();
                    idParams.add(menuitem.getId());
                    params.put(menuClientId + "_menuid", idParams);

                    command = menuitem.isAjax() ? buildAjaxRequest(context, menu, (AjaxSource) menuitem, (UIForm)form, params) : buildNonAjaxRequest(context, menu, form, menuClientId, params, true);
                } else {
                    command = menuitem.isAjax() ? buildAjaxRequest(context, (AjaxSource) menuitem, (UIForm) form) : buildNonAjaxRequest(context, ((UIComponent) menuitem), form, ((UIComponent) menuitem).getClientId(context), true);
                }

                onclick = (onclick == null) ? command : onclick + ";" + command;
            }

            if (onclick != null) {
                writer.writeAttribute("onclick", onclick, null);
            }
        }

        encodeMenuItemContent(context, menu, menuitem);

        writer.endElement("a");
    }

    @Override
    protected void encodeMenuItemContent(FacesContext context, AbstractMenu menu, MenuItem menuitem) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String icon = menuitem.getIcon();
        Object value = menuitem.getValue();

        if (icon != null) {
            writer.startElement("i", null);
            writer.writeAttribute("class", icon, null);
            writer.endElement("i");
        }

        if (value != null) {
            boolean isRootMenu = (menuitem instanceof DefaultMenuItem) ? isParentRootMenu(menuitem.getId()) : isParentRootMenu((UIComponent) menuitem);
            if (isRootMenu) {
                writer.startElement("span", null);
                writer.writeAttribute("class", "menu-text-group", null);
            }

            writer.startElement("span", null);
            writer.writeAttribute("class", "menu-text", null);
            writer.writeText(value, "value");
            writer.endElement("span");

            if (isRootMenu) {
                writer.endElement("span");
            }
        }
    }

    @Override
    protected void encodeScript(FacesContext context, AbstractMenu abstractMenu) throws IOException {
        IcarusMenu menu = (IcarusMenu) abstractMenu;
        String clientId = menu.getClientId(context);
        WidgetBuilder wb = getWidgetBuilder(context);
        wb.init("Icarus", menu.resolveWidgetVar(), clientId).finish();
    }

}
