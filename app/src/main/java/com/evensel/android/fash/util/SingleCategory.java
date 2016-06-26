
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
    "total",
    "per_page",
    "current_page",
    "last_page",
    "next_page_url",
    "prev_page_url",
    "from",
    "to",
    "data"
})
public class SingleCategory {

    @JsonProperty("total")
    private Integer total;
    @JsonProperty("per_page")
    private Integer perPage;
    @JsonProperty("current_page")
    private Integer currentPage;
    @JsonProperty("last_page")
    private Integer lastPage;
    @JsonProperty("next_page_url")
    private Object nextPageUrl;
    @JsonProperty("prev_page_url")
    private Object prevPageUrl;
    @JsonProperty("from")
    private Integer from;
    @JsonProperty("to")
    private Integer to;
    @JsonProperty("data")
    private List<Datum> data = new ArrayList<Datum>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The total
     */
    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The perPage
     */
    @JsonProperty("per_page")
    public Integer getPerPage() {
        return perPage;
    }

    /**
     * 
     * @param perPage
     *     The per_page
     */
    @JsonProperty("per_page")
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    /**
     * 
     * @return
     *     The currentPage
     */
    @JsonProperty("current_page")
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 
     * @param currentPage
     *     The current_page
     */
    @JsonProperty("current_page")
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 
     * @return
     *     The lastPage
     */
    @JsonProperty("last_page")
    public Integer getLastPage() {
        return lastPage;
    }

    /**
     * 
     * @param lastPage
     *     The last_page
     */
    @JsonProperty("last_page")
    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * 
     * @return
     *     The nextPageUrl
     */
    @JsonProperty("next_page_url")
    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    /**
     * 
     * @param nextPageUrl
     *     The next_page_url
     */
    @JsonProperty("next_page_url")
    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    /**
     * 
     * @return
     *     The prevPageUrl
     */
    @JsonProperty("prev_page_url")
    public Object getPrevPageUrl() {
        return prevPageUrl;
    }

    /**
     * 
     * @param prevPageUrl
     *     The prev_page_url
     */
    @JsonProperty("prev_page_url")
    public void setPrevPageUrl(Object prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    /**
     * 
     * @return
     *     The from
     */
    @JsonProperty("from")
    public Integer getFrom() {
        return from;
    }

    /**
     * 
     * @param from
     *     The from
     */
    @JsonProperty("from")
    public void setFrom(Integer from) {
        this.from = from;
    }

    /**
     * 
     * @return
     *     The to
     */
    @JsonProperty("to")
    public Integer getTo() {
        return to;
    }

    /**
     * 
     * @param to
     *     The to
     */
    @JsonProperty("to")
    public void setTo(Integer to) {
        this.to = to;
    }

    /**
     * 
     * @return
     *     The data
     */
    @JsonProperty("data")
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    @JsonProperty("data")
    public void setData(List<Datum> data) {
        this.data = data;
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
