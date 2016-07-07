package com.evensel.android.fash.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.evensel.android.fash.AppController;
import com.evensel.android.fash.util.FeaturedProduct;
import com.evensel.android.fash.util.HomeSuperCategory;
import com.evensel.android.fash.util.ShopDetail;
import com.evensel.android.fash.util.SingleCategory;
import com.evensel.android.fash.util.SingleProduct;
import com.evensel.android.fash.util.SuperCategory;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prishanm 06/01/2016
 *
 */
public class JsonRequestManager {

	private static JsonRequestManager mInstance;
	private RequestQueue mRequestQueue;
	private static Context mCtx;

	/**
	 * Log or request TAG
	 */
	public static final String TAG = "VolleyInstance";
	private String tag_json_arry = "json_array_req";

	/* Volley */
	public static synchronized JsonRequestManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new JsonRequestManager(context);
		}
		return mInstance;
	}

	public JsonRequestManager(Context context) {
		mCtx = context;
		mRequestQueue = getRequestQueue();
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley
					.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	}

	/******************************************************************************************************************************************/

	/* Volley */

	/**
	 * Get Featured Products Requests
	 **/
	public static interface getFeaturedProductsRequest {
		void onSuccess(List<FeaturedProduct> featuredProducts);

		void onError(String status);
	}
	
	public void getFeaturedProducts(String url,String superCategory,
			final getFeaturedProductsRequest callback) {

		String fullUrl = url+"superCategory="+superCategory;

		JsonArrayRequest req = new JsonArrayRequest(fullUrl,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d("responseString", response.toString());
						ObjectMapper mapper = new ObjectMapper();
						List<FeaturedProduct> featuredProductList = new ArrayList<FeaturedProduct>();

						try {
							for (int i = 0; i < response.length(); i++) {
								JSONObject jObj = response.getJSONObject(i);
								FeaturedProduct product = mapper.readValue(jObj.toString(), FeaturedProduct.class);
								featuredProductList.add(product);
							}
							callback.onSuccess(featuredProductList);
							mapper = null;
						} catch (Exception e) {
							callback.onError("Error occured");
						}
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						callback.onError(VolleyErrorHelper.getMessage(error,
								mCtx));
					}
		});
		
		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

		//AppController.getInstance().getRequestQueue().cancelAll(tag_json_arry);

	}


	/******************************************************************************************************************************************/

	/**
	 * Get Single products Requests
	 **/
	public static interface getSingleProductRequest {
		void onSuccess(SingleProduct singleProduct);

		void onError(String status);
	}

	public void getSingleProducts(String url,int id,
								 final getSingleProductRequest callback) {


		String fullUrl = url+id;

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, fullUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						ObjectMapper mapper = new ObjectMapper();
						try {
							Log.d("xxxx", response.toString());
							SingleProduct singleProductDetails = mapper.readValue(response.toString(), SingleProduct.class);

							callback.onSuccess(singleProductDetails);
							mapper = null;
						} catch (Exception e) {
							callback.onError(e.getLocalizedMessage());
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onError(VolleyErrorHelper.getMessage(volleyError,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}



	/******************************************************************************************************************************************/

	/**
	 * Get Category - Product list Requests
	 **/
	public static interface getProductListRequest {
		void onSuccess(SingleCategory singleCategory);

		void onError(String status);
	}

	public void getProductList(String url,int shopId,int category,int page,
								  final getProductListRequest callback) {

		String fullUrl = "";
		if(shopId!=0){
			fullUrl = url+"shop="+shopId+"&category="+category+"&page="+page;
		}else{
			fullUrl = url+"category="+category+"&page="+page;
		}

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, fullUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						ObjectMapper mapper = new ObjectMapper();
						try {
							Log.d("xxxx", response.toString());
							SingleCategory categoryProducts = mapper.readValue(response.toString(), SingleCategory.class);

							callback.onSuccess(categoryProducts);
							mapper = null;
						} catch (Exception e) {
							callback.onError(e.getLocalizedMessage());
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onError(VolleyErrorHelper.getMessage(volleyError,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/

	/**
	 * Get Product list  for Search query(Master Search) Requests
	 **/
	public static interface getMasterSearchListRequest {
		void onSuccess(SingleCategory singleCategory);

		void onError(String status);
	}

	public void getMasterSearchList(String url,String query,int page,
							   final getMasterSearchListRequest callback) {


		String fullUrl = url+"query="+query.replace(" ","+")+"&page="+page;

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, fullUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						ObjectMapper mapper = new ObjectMapper();
						try {
							Log.d("xxxx", response.toString());
							SingleCategory categoryProducts = mapper.readValue(response.toString(), SingleCategory.class);

							callback.onSuccess(categoryProducts);
							mapper = null;
						} catch (Exception e) {
							callback.onError(e.getLocalizedMessage());
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onError(VolleyErrorHelper.getMessage(volleyError,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/


	/**
	 * Get Product list  for Search query(Shopwise) Requests
	 **/
	public static interface getShopCategorySearchListRequest {
		void onSuccess(SingleCategory singleCategory);

		void onError(String status);
	}

	public void getShopCategorySearchList(String url,String query,int id,int category,int page,
								  final getShopCategorySearchListRequest callback) {


		String fullUrl = url+"shop="+id+"&query="+query.replace(" ","+")+"&category="+category+"&page="+page;

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, fullUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						ObjectMapper mapper = new ObjectMapper();
						try {
							Log.d("xxxx", response.toString());
							SingleCategory categoryProducts = mapper.readValue(response.toString(), SingleCategory.class);

							callback.onSuccess(categoryProducts);
							mapper = null;
						} catch (Exception e) {
							callback.onError(e.getLocalizedMessage());
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onError(VolleyErrorHelper.getMessage(volleyError,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/

	/**
	 * Get Categories of Super Categories(NOT inside shops) Requests
	 **/
	public static interface getSuperCategoryCategoryListRequest {
		void onSuccess(List<HomeSuperCategory> homeSuperCategoryList);

		void onError(String status);
	}

	public void getSuperCategoryCategoryList(String url,String superCategory,final getSuperCategoryCategoryListRequest callback) {


		String fullUrl = url+"superCategory="+superCategory;

		JsonArrayRequest req = new JsonArrayRequest(fullUrl,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d("responseString xxx", response.toString());
						ObjectMapper mapper = new ObjectMapper();
						List<HomeSuperCategory> categories = new ArrayList<HomeSuperCategory>();

						try {
							for (int i = 0; i < response.length(); i++) {
								JSONObject jObj = response.getJSONObject(i);
								HomeSuperCategory superCategory = mapper.readValue(jObj.toString(), HomeSuperCategory.class);
								categories.add(superCategory);
							}
							callback.onSuccess(categories);
							mapper = null;
						} catch (Exception e) {
							callback.onError("Error occured");
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(VolleyErrorHelper.getMessage(error,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/


	/**
	 * Get All Shops Requests
	 **/
	public static interface getAllShopsRequest{
		void onSuccess(List<ShopDetail> shopDetails);

		void onError(String status);
	}

	public void getAllShops(String url,
								 final getAllShopsRequest callback) {

		String fullUrl = url;

		JsonArrayRequest req = new JsonArrayRequest(fullUrl,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d("responseString", response.toString());
						ObjectMapper mapper = new ObjectMapper();
						List<ShopDetail> shopDetailList = new ArrayList<ShopDetail>();

						try {
							for (int i = 0; i < response.length(); i++) {
								JSONObject jObj = response.getJSONObject(i);
								ShopDetail shop = mapper.readValue(jObj.toString(), ShopDetail.class);
								shopDetailList.add(shop);
							}
							callback.onSuccess(shopDetailList);
							mapper = null;
						} catch (Exception e) {
							callback.onError("Error occured");
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(VolleyErrorHelper.getMessage(error,
						mCtx));
			}
		});

		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

		// Cancelling request
		//AppController.getInstance().getRequestQueue().cancelAll(tag_json_arry);

	}


	/******************************************************************************************************************************************/

	/**
	 * Get Super Categories Requests
	 **/
	public static interface getShopSuperCategoriesRequest {
		void onSuccess(List<SuperCategory> superCategoryList);

		void onError(String status);
	}

	public void getShopSuperCategories(String url,int shopId,final getShopSuperCategoriesRequest callback) {


		String fullUrl = url+"shop="+shopId;

		JsonArrayRequest req = new JsonArrayRequest(fullUrl,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d("responseString xxx", response.toString());
						ObjectMapper mapper = new ObjectMapper();
						List<SuperCategory> categoriesList = new ArrayList<SuperCategory>();

						try {
							for (int i = 0; i < response.length(); i++) {
								JSONObject jObj = response.getJSONObject(i);
								SuperCategory superCategory = mapper.readValue(jObj.toString(), SuperCategory.class);
								categoriesList.add(superCategory);
							}
							callback.onSuccess(categoriesList);
							mapper = null;
						} catch (Exception e) {
							callback.onError("Error occured");
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(VolleyErrorHelper.getMessage(error,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/

	/**
	 * Get Categories of Super Categories(Inside shops) Requests
	 **/
	public static interface getShopCategoryListRequest {
		void onSuccess(List<HomeSuperCategory> homeSuperCategoryList);

		void onError(String status);
	}

	public void getShopCategoryList(String url,String superCategory,int shopId,final getShopCategoryListRequest callback) {


		String fullUrl = url+"superCategory="+superCategory+"&shop="+shopId;

		JsonArrayRequest req = new JsonArrayRequest(fullUrl,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						ObjectMapper mapper = new ObjectMapper();
						List<HomeSuperCategory> categories = new ArrayList<HomeSuperCategory>();

						try {
							for (int i = 0; i < response.length(); i++) {
								JSONObject jObj = response.getJSONObject(i);
								HomeSuperCategory superCategory = mapper.readValue(jObj.toString(), HomeSuperCategory.class);
								categories.add(superCategory);
							}
							callback.onSuccess(categories);
							mapper = null;
						} catch (Exception e) {
							callback.onError("Error occured");
						}

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callback.onError(VolleyErrorHelper.getMessage(error,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/

	/**
	 * Get Product list  for Search query(Shopwise - Super category) Requests
	 **/
	public static interface getShopSuperCategorySearchListRequest {
		void onSuccess(SingleCategory singleCategory);

		void onError(String status);
	}

	public void getShopSuperCategorySearchList(String url,String query,int id,int superCategory,int page,
										  final getShopSuperCategorySearchListRequest callback) {

		String fullUrl = "";
		if(superCategory!=0)
			fullUrl = url+"query="+query.replace(" ","+")+"&shop="+id+"&superCategory="+superCategory+"&page="+page;
		else
			fullUrl = url+"query="+query.replace(" ","+")+"&shop="+id+"&page="+page;

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, fullUrl, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						ObjectMapper mapper = new ObjectMapper();
						try {

							SingleCategory categoryProducts = mapper.readValue(response.toString(), SingleCategory.class);

							callback.onSuccess(categoryProducts);
							mapper = null;
						} catch (Exception e) {
							callback.onError("No data.");
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				callback.onError(VolleyErrorHelper.getMessage(volleyError,
						mCtx));
			}
		});


		req.setRetryPolicy(new DefaultRetryPolicy(30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req,
				tag_json_arry);

	}


	/******************************************************************************************************************************************/
	
}
