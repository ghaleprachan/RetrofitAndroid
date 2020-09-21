package com.example.retrofitlatest.retrofitInterface;


import com.example.retrofitlatest.models.Comments;
import com.example.retrofitlatest.models.PostModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderAPI {
    //    TODO GET Methods
    /*
     * There in GET posts is a relative URL except base URL
     * Call object is a retrofit method which will call data from URL*/
    @GET("posts")
    Call<List<PostModel>> getPosts();

    /*
     * The following code is to get specific value from the DB
     * {id} takes the id which is passed as variable in get comments method
     * @Path("id") is to represents that the passed value is the id into the link
     * TODO "posts/4/comments"*/
    @GET("posts/{id}/comments")
    Call<List<Comments>> getComments(@Path("id") int commentId);

    /*
     * This is used when link is like "posts?userId=1"
     * TODO "posts?userId=1"*/
    @GET("posts")
    Call<List<PostModel>> getPostByQuery(@Query("userId") int userId);

    /*
     * This is used when link is like"
     * TODO "posts?userId=1&_sort=id&_order=desc"
     *  if we want to pass multiple user id at once than we can pass list of integer like this
     * @Query("userId") List<Integer> user id */
    @GET("posts")
    Call<List<PostModel>> getPostMultQuery(@Query("userId") int userId,
                                           @Query("_sort") String id,
                                           @Query("_order") String order);

    /*
     * This method can be used when you want to send multiple parameter in API link without defining them as method arguments
     * For this we will use QueryMap and MAP<String, String> as method parameter
     * This method decides the parameters only when we call this method
     * In map we can put some key value pairs
     * TODO
     *  Key is 'userId' and value is '2'*/
    @GET("posts")
    Call<List<PostModel>> getPostQueryMap(@QueryMap Map<String, String> parameters);

    /*
     * In another way without using any of the above we can just pass URL*/
    @GET
    Call<List<Comments>> getCommentsByURL(@Url String urlHere);


    //    TODO Post Method
    /*
     *  From down here we will be working on post form using retrofit
     * the Call<PostModel> is the return type of the method
     * This is used when we send data into the json format to server*/
    @POST("posts")
    Call<PostModel> createPost(@Body PostModel post);

    /*
     * The code down is used when we have to send data to URL in form of XML
     * We also can use other converters than GSON
     * This will look like this in url
     * Some of the APIs only accept this kinds of data
     * TODO userId=23&title=newTitle&body=bodyText*/
    @FormUrlEncoded
    @POST("posts")
    Call<PostModel> createPostXML(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    /*
     * Instead of using above thing we also can use MAP just like in get*/
    @FormUrlEncoded
    @POST("posts")
    Call<PostModel> createPostMap(@FieldMap Map<String, String> values);


    //    TODO PUT and PATCH are used for updating the data
    /*
     * The PUT and PATCH are same which are used to update data into the server
     * However put is used to completely replace the data with the new object sent
     * Whereas in patch only the fields that are send, if we sent only title it will update only title from the database*/
    @PUT("posts/{id}")
    Call<PostModel> putPosts(@Path("id") int id, @Body PostModel post);

    @PATCH("posts/{id}")
    Call<PostModel> patchPosts(@Path("id") int id, @Body PostModel post);


    // TODO DELETE Method is retrofit
    /*
    * We do return type Call<Void> because the API returns body less value from the server*/
    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
