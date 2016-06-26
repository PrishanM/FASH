
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
    "title",
    "price",
        "code",
    "sizes",
    "description",
    "in_stock",
    "images",
    "locations"
})
public class SingleProduct {

    @JsonProperty("title")
    private String title;
    @JsonProperty("price")
    private String price;
    @JsonProperty("code")
    private String code;
    @JsonProperty("sizes")
    private List<String> sizes = new ArrayList<String>();
    @JsonProperty("description")
    private String description;
    @JsonProperty("in_stock")
    private Boolean inStock;
    @JsonProperty("images")
    private List<String> images = new ArrayList<String>();
    @JsonProperty("locations")
    private List<Location> locations = new ArrayList<Location>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The price
     */
    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     *     The code
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     *     The code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The sizes
     */
    @JsonProperty("sizes")
    public List<String> getSizes() {
        return sizes;
    }

    /**
     * 
     * @param sizes
     *     The sizes
     */
    @JsonProperty("sizes")
    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
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
     *     The inStock
     */
    @JsonProperty("in_stock")
    public Boolean getInStock() {
        return inStock;
    }

    /**
     * 
     * @param inStock
     *     The in_stock
     */
    @JsonProperty("in_stock")
    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    /**
     * 
     * @return
     *     The images
     */
    @JsonProperty("images")
    public List<String> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    @JsonProperty("images")
    public void setImages(List<String> images) {
        this.images = images;
    }

    /**
     * 
     * @return
     *     The locations
     */
    @JsonProperty("locations")
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * 
     * @param locations
     *     The locations
     */
    @JsonProperty("locations")
    public void setLocations(List<Location> locations) {
        this.locations = locations;
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
