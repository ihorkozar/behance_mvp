package i.kozar.behance_mvp.data.api;


import i.kozar.behance_mvp.data.model.project.ProjectResponse;
import i.kozar.behance_mvp.data.model.user.UserResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BehanceApi {

    @GET("v2/projects")
    Single<ProjectResponse> getProjects(@Query("q") String query);

    @GET("v2/users/{username}")
    Single<UserResponse> getUserInfo(@Path("username") String username);

    @GET("v2/users/{username}/projects")
    Single<ProjectResponse> getUserProjects(@Path("username") String username);
}
