
package com.evensel.android.fash.util;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "contacts",
    "slogan",
    "open_hours",
    "logo",
    "imageURLs"
})
public class ShopDetail {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("contacts")
    private String contacts;
    @JsonProperty("slogan")
    private String slogan;
    @JsonProperty("open_hours")
    private String openHours;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("imageURLs")
    private List<String> imageURLs = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The contacts
     */
    @JsonProperty("contacts")
    public String getContacts() {
        return contacts;
    }

    /**
     * 
     * @param contacts
     *     The contacts
     */
    @JsonProperty("contacts")
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * 
     * @return
     *     The slogan
     */
    @JsonProperty("slogan")
    public String getSlogan() {
        return slogan;
    }

    /**
     * 
     * @param slogan
     *     The slogan
     */
    @JsonProperty("slogan")
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    /**
     * 
     * @return
     *     The openHours
     */
    @JsonProperty("open_hours")
    public String getOpenHours() {
        return openHours;
    }

    /**
     * 
     * @param openHours
     *     The open_hours
     */
    @JsonProperty("open_hours")
    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    /**
     * 
     * @return
     *     The logo
     */
    @JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    /**
     * 
     * @param logo
     *     The logo
     */
    @JsonProperty("logo")
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 
     * @return
     *     The imageURLs
     */
    @JsonProperty("imageURLs")
    public List<String> getImageURLs() {
        return imageURLs;
    }

    /**
     * 
     * @param imageURLs
     *     The imageURLs
     */
    @JsonProperty("imageURLs")
    public void setImageURLs(List<String> imageURLs) {
        this.imageURLs = imageURLs;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
