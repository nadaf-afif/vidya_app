package com.sudosaints.cmavidya.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Vishal on 7/30/2014.
 */
public interface CMAVidyaService {

	/**
	 * Few Examples
	 *
	 * @GET(value = "/getUsers")
	 * public void getAllUsers(Callback<ApiResponse> callback);
	 * @GET(value = "/user/{id}")
	 * public void getUserDetails(@Path("id") String id, Callback<ApiResponse> callback);
	 * @POST("/user/edit") void editUserProfile(@Query("first_name") String firstName, @Query("last_name") String lastName, @Query("email") String email, @Query("phone") String phone, Callback<ApiResponse> callback);
	 */

	@GET(value = "/Authenticate.svc/getUsers")
	public void getAllUsers(Callback<ApiResponse> callback);

	@GET(value = "/Authenticate.svc/GetCourseList")
	public void getCourses(Callback<Object> callback);

	@POST(value = "/Authenticate.svc/Registration")
	public void registerUser(@Body Object jsonObject, Callback<Object> callback);

	@GET(value = "/Authenticate.svc/SignIn")
	public void loginUser(@Query("UserName") String UserName, @Query("Password") String Password, Callback<Object> callback);

	@GET(value = "/Planner.svc/Mob_GetMyPlanEvents/{username}")
	public void getMyPlanDetails(@Path("username") String username, Callback<Object> callback);

	@POST(value = "/Planner.svc/UserPostPonePrePone")
	public void postPerponePostPonePlanEvent(@Body Object o, Callback<Object> objectCallback);

	@GET(value = "/UserPDFKeyNotes.svc/GetPDFKeyNotes")
	public void getKeyNotes(Callback<Object> callback);

	@GET(value = "/Planner.svc/GetMobileTopicReplanUserDatemapping/{username}")
	public void getTopicReplanData(@Path("username") String userName, Callback<Object> objectCallback);

	@GET(value = "/Planner.svc/GetMobileSubjectDateMappingDetails/{username}")
	public void getSubjectReplanData(@Path("username") String userName, Callback<Object> objectCallback);

	@GET(value = "/Planner.svc/GetUserPlannerInfo/{username}")
	public void getUserPlannerInfo(@Path("username") String username, Callback<Object> objectCallback);

	@POST(value = "/Planner.svc/SubjectDatemappingList")
	public void updateSubjectReplanData(@Body Object o, Callback<Object> objectCallback);

	@POST(value = "/Planner.svc/TopicDatemappingList")
	public void updateTopicReplanData(@Body Object o, Callback<Object> objectCallback);

	@POST(value = "/Planner.svc/PostPoneByDay")
	public void postponeByDay(@Body Object o, Callback<Object> objectCallback);

	@GET(value = "/Auth.svc/GetQuesSubjectList/{username}")
	public void getSubjectListForTest(@Path("username") String username, Callback<Object> objectCallback);

	@GET(value = "/Auth.svc/GetQuesTopicList/{username}")
	public void getTopicListForTest(@Path("username") String username, Callback<Object> objectCallback);

	@POST(value = "/Auth.svc/GenerateTopicTest")
	public void generateTopicTest(@Body Object o, Callback<Object> objectCallback);

	@POST(value = "/Auth.svc/GenerateSubjectTest")
	public void generateSubjectTest(@Body Object o, Callback<Object> objectCallback);

	@POST(value = "/Auth.svc/GeneratePreviousTest")
	public void generatePreviousTest(@Body Object o, Callback<Object> objectCallback);

	@POST(value = "/Auth.svc/GenerateFixedTest")
	public void generateFixedTest(@Body Object o, Callback<Object> objectCallback);


	@GET(value = "/Auth.svc/LoadTest/{username}")
	public void loadTest(@Path("username") String username, @Query("Id") long idTestLogMaster, Callback<Object> objectCallback);

	@POST(value = "/Auth.svc/UpdateAnswer")
	public void updateAnswer(@Body Object o, Callback<Object> objectCallback);


	@GET(value = "/Auth.svc/GetPreviousTestList/{UserName}")
	public void getAllPreviousTestList(@Path("UserName") String userName, Callback<Object> objectCallback);

	@GET(value = "/Auth.svc/GetTemplateList/{UserName}")
	public void getTemplateTestList(@Path("UserName") String userName, Callback<Object> objectCallback);

	@GET(value = "/Auth.svc/GetFixedTestList/{UserName}")
	public void getFixedTestList(@Path("UserName") String userName, Callback<Object> objectCallback);

	@GET(value = "/Auth.svc/GetCompletedTest/{UserName}")
	public void getTestDependingOnIsTestEnd(@Path("UserName") String username, @Query("IsEnd") boolean isEnd, Callback<Object> objectCallback);

	@POST(value = "/Auth.svc/GenerateTemplateTest")
	public void genrateMcqTemplateTest(@Body Object o, Callback<Object> objectCallback);

	@POST(value = "/Auth.svc/GenerateTheoryTemplateTest")
	public void genrateTheoryTemplateTest(@Body Object o, Callback<Object> objectCallback);

	@GET(value = "/ForumSrv.svc/GetMasterForums/{courseId}")
	public void getMasterForumTopics(@Path("courseId") int courseId, Callback<Object> objectCallback);

	@GET(value = "/ForumSrv.svc/GetForumSubjectByForumMasterId/{ForumMasterID}")
	public void getForumSubjectByForumMasterId(@Path("ForumMasterID") int forumMasterID, Callback<Object> objectCallback);

	@GET(value = "/ForumSrv.svc/GetForumsBySubjectId/{ID}")
	public void getForumBySubjectId(@Path("ID") int forumMasterId, Callback<Object> objectCallback);

	/*@GET(value = "/ForumSrv.svc/GetForumByID/{ID}")
	public void getForumById(@Path("ID") int id , Callback<Object> objectCallback);*/

	@GET(value = "/ForumSrv.svc/GetThreads/{ID}/{StartRowIndex}/{MaximumRows}")
	public void getForumThreadsByForumId(@Path("ID") long id, @Path("StartRowIndex") int startRowIndex, @Path("MaximumRows") int maxRows, Callback<Object> objectCallback);

	@GET(value = "/ForumSrv.svc/GetThreadByID/{ID}")
	public void getForumThread(@Path("ID") long id, Callback<Object> objectCallback);

	@GET(value = "/ForumSrv.svc/CloseThread/{ID}")
	public void closeThread(@Path("ID") long id, Callback<Object> objectCallback);

	@POST(value = "/ForumSrv.svc/InsertPost")
	public void insertPost(@Body Object o, Callback<Object> objectCallback);

	@POST(value = "/ForumSrv.svc/UpdatePost")
	public void updatePost(@Body Object o, Callback<Object> objectCallback);


	@POST(value = "/Planner.svc/MarkUserEvent")
	public void createDeleteUserEvents(@Body Object o, Callback<Object> objectCallback);

	@GET(value = "/OtherServices.svc/GetUserVideos/{ID}")
	public void getUserVideos(@Path("ID") String userId, Callback<Object> objectCallback);

}
