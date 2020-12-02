package com.example.retrofitlatest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.retrofitlatest.models.Comments;
import com.example.retrofitlatest.models.PostModel;
import com.example.retrofitlatest.retrofitInterface.JsonPlaceHolderAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class MainActivity extends AppCompatActivity {
    private TextView textResult;
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.textResult);

        /*
         * Calling retrofit by creating its instance
         * Here we will enter the base URL
         * This part will be done in repository in MVVM architecture
         * In addConverterFactory we pass what tool we use to convert the data in this case we use GSON
         * The BaseURL has to end with
         * TODO If we want gson in patch to accept the null values we must do so
         *  Make a gson object and pass to GsonConverterFactory.create(gson)
         *  if not don't do anything leave it as it is*/

        /*
         * TODO to track what data we are sending to out URL and getting in response
         *  we have to use another library and add dependency
         *  After that we need to add client in retrofit instance
         *  for this we use HttpLoggingInterceptor*/
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // Setting level as BODY will track everything
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        /*To accept the null values we should do this*/
        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        /*
         * With above retrofit we can now create JSON Placeholder API*/
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
         getPosts();
        // getComments();
        // getPostsByQuery();
        // getPostMultQuery();
        // getPostQueryMap();
        // getCommentsByURL();
        // createPost();
        // createPostXML();
        // createPostMap();
//        updatePostPut();
        // updatePostPatch();
        // deletePost();
    }

    // TODO this is for DELETE
    private void deletePost() {
        /*Retrofit will make a implementation for this method automatically
        * There for we simple call method make making API using retrofit instance*/
        Call<Void> call = jsonPlaceHolderAPI.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @SuppressLint("SetTextI18n")
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Void> call, Response<Void> response) {
                textResult.setText("CODE: " + response.code());
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Void> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO this is for PUT
    private void updatePostPut() {
        PostModel model = new PostModel(12, null, "This is the newest one.");
        Call<PostModel> call = jsonPlaceHolderAPI.putPosts(5, model);
        call.enqueue(new Callback<PostModel>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (!response.isSuccessful()) {
                    textResult.setText(response.code());
                    return;
                }
                PostModel postResponse = response.body();
                String content = "";
                assert postResponse != null;
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Body: " + postResponse.getText();
                textResult.append(content);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PostModel> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    /*
     * TODO when we do patch and send the null value for title it will not replace title by null
     *  Instead it will keep the title same as default*/
    private void updatePostPatch() {
        PostModel model = new PostModel(12, null, "This is the newest one.");
        Call<PostModel> call = jsonPlaceHolderAPI.patchPosts(5, model);
        call.enqueue(new Callback<PostModel>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (!response.isSuccessful()) {
                    textResult.setText(response.code());
                    return;
                }
                PostModel postResponse = response.body();
                String content = "";
                assert postResponse != null;
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Body: " + postResponse.getText();
                textResult.append(content);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PostModel> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO Post XML Url Request Using MAP
    private void createPostMap() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", "12");
        map.put("title", "New Title");
        Call<PostModel> call = jsonPlaceHolderAPI.createPostMap(map);
        call.enqueue(new Callback<PostModel>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (!response.isSuccessful()) {
                    textResult.setText(response.code());
                    return;
                }
                PostModel postResponse = response.body();
                String content = "";
                assert postResponse != null;
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Body: " + postResponse.getText();
                textResult.append(content);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PostModel> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO Post XML Url Request
    private void createPostXML() {
        Call<PostModel> call = jsonPlaceHolderAPI.createPostXML(23, "New Title", "New Text");
        call.enqueue(new Callback<PostModel>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (!response.isSuccessful()) {
                    textResult.setText(response.code());
                    return;
                }
                PostModel postResponse = response.body();
                String content = "";
                assert postResponse != null;
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getUserId() + "\n";
                content += "Body: " + postResponse.getText();
                textResult.append(content);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PostModel> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    /*TODO POST JSON Request
     * This is to post model or form into the database using APIs*/
    private void createPost() {
        PostModel model = new PostModel(23, "New Title", "New Text");
        Call<PostModel> call = jsonPlaceHolderAPI.createPost(model);
        call.enqueue(new Callback<PostModel>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                if (!response.isSuccessful()) {
                    textResult.setText(response.code());
                    return;
                }
                PostModel postResponse = response.body();
                String content = "";
                assert postResponse != null;
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getUserId() + "\n";
                content += "Body: " + postResponse.getText();
                textResult.append(content);
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PostModel> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO Get
    private void getCommentsByURL() {
        Call<List<Comments>> call = jsonPlaceHolderAPI.getCommentsByURL("posts/1/comments");
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                try {
                    if (!response.isSuccessful()) {
                        textResult.setText(response.code());
                        return;
                    }
                    List<Comments> comments = response.body();
                    assert comments != null;
                    for (Comments comment : comments) {
                        String content = "";
                        content += "ID: " + comment.getId() + "\n";
                        content += "Post ID: " + comment.getPostID() + "\n";
                        content += "Name: " + comment.getName() + "\n";
                        content += "Email: " + comment.getEmail() + "\n";
                        content += "Body: " + comment.getBody() + "\n\n";
                        textResult.append(content);
                    }
                } catch (Exception ex) {
                    textResult.setText(ex.getMessage());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO Get
    private void getPostQueryMap() {
        /*
         * This is to pass map as a parameter
         * The downside of using map is that we can't send same value for multiple time
         * TODO
         *  EG: we can't send two user id's*/
        Map<String, String> parameter = new HashMap<>();
        parameter.put("userId", "1");
        parameter.put("_sort", "id");
        parameter.put("_order", "desc");
        Call<List<PostModel>> call = jsonPlaceHolderAPI.getPostQueryMap(parameter);
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                try {
                    if (!response.isSuccessful()) {
                        textResult.setText(response.code());
                        return;
                    }
                    List<PostModel> postModels = response.body();
                    assert postModels != null;
                    for (PostModel postModel : postModels) {
                        String content = "";
                        content += "ID: " + postModel.getId() + "\n";
                        content += "User ID: " + postModel.getUserId() + "\n";
                        content += "Title: " + postModel.getUserId() + "\n\n";
                        content += "Body: " + postModel.getText();
                        textResult.append(content);
                    }
                } catch (Exception ex) {
                    textResult.setText(ex.getMessage());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO Get
    private void getPostMultQuery() {
        /*
         * If we don't any of the parameter than just pass null
         * If its int u don't want to pass than change int to Integer*/
        Call<List<PostModel>> call = jsonPlaceHolderAPI.getPostMultQuery(4, "id", "desc");
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                try {
                    if (!response.isSuccessful()) {
                        textResult.setText(response.code());
                        return;
                    }
                    List<PostModel> postModels = response.body();
                    assert postModels != null;
                    for (PostModel postModel : postModels) {
                        String content = "";
                        content += "ID: " + postModel.getId() + "\n";
                        content += "User ID: " + postModel.getUserId() + "\n";
                        content += "Title: " + postModel.getUserId() + "\n\n";
                        content += "Body: " + postModel.getText();
                        textResult.append(content);
                    }
                } catch (Exception ex) {
                    textResult.setText(ex.getMessage());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO Get
    private void getPostsByQuery() {
        Call<List<PostModel>> call = jsonPlaceHolderAPI.getPostByQuery(4);
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                try {
                    if (!response.isSuccessful()) {
                        textResult.setText(response.code());
                        return;
                    }
                    List<PostModel> postModels = response.body();
                    assert postModels != null;
                    for (PostModel postModel : postModels) {
                        String content = "";
                        content += "ID: " + postModel.getId() + "\n";
                        content += "User ID: " + postModel.getUserId() + "\n";
                        content += "Title: " + postModel.getUserId() + "\n\n";
                        content += "Body: " + postModel.getText();
                        textResult.append(content);
                    }
                } catch (Exception ex) {
                    textResult.setText(ex.getMessage());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO Get
    private void getComments() {
        Call<List<Comments>> call = jsonPlaceHolderAPI.getComments(2);
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                try {
                    if (!response.isSuccessful()) {
                        textResult.setText(response.code());
                        return;
                    }
                    List<Comments> comments = response.body();
                    assert comments != null;
                    for (Comments comment : comments) {
                        String content = "";
                        content += "ID: " + comment.getId() + "\n";
                        content += "Post ID: " + comment.getPostID() + "\n";
                        content += "Name: " + comment.getName() + "\n";
                        content += "Email: " + comment.getEmail() + "\n";
                        content += "Body: " + comment.getBody() + "\n\n";
                        textResult.append(content);
                    }
                } catch (Exception ex) {
                    textResult.setText(ex.getMessage());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    // TODO Get
    private void getPosts() {
        Call<List<PostModel>> call = jsonPlaceHolderAPI.getPosts();
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                try {
                    if (!response.isSuccessful()) {
                        textResult.setText(response.code());
                        return;
                    }
                    List<PostModel> postModels = response.body();
                    assert postModels != null;
                    for (PostModel postModel : postModels) {
                        String content = "";
                        content += "ID: " + postModel.getId() + "\n";
                        content += "User ID: " + postModel.getUserId() + "\n";
                        content += "Title: " + postModel.getUserId() + "\n\n";
                        content += "Body: " + postModel.getText();
                        textResult.append(content);
                    }
                } catch (Exception ex) {
                    textResult.setText(ex.getMessage());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }
}