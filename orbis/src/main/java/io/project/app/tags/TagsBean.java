package io.project.app.tags;

import io.project.app.domain.Tag;
import io.project.app.security.SessionContext;
import io.project.app.unicorn.TagClient;

import java.io.Serializable;
import java.util.List;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

@Named
@ViewScoped
@Data
public class TagsBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(TagsBean.class);

    @Inject
    private TagClient tagClient;

    private TagCloudModel model;

    @Inject
    private SessionContext sessionContext = null;

    public TagsBean() {
    }

    @PostConstruct
    public void init() {
        LOGGER.info("TagsBean Bean called");
        model = new DefaultTagCloudModel();
        List<Tag> tagList = this.getTagList();
        tagList.forEach((tt) -> {
            model.addTag(new DefaultTagCloudItem(tt.getTag(), tt.hashCode()));
        });       

    }

    public List<Tag> getTagList() {
        return tagClient.findAllTags().getTagList();
    }

    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", item.getLabel());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

}
