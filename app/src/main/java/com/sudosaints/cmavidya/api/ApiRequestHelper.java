package com.sudosaints.cmavidya.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.okhttp.OkHttpClient;
import com.sudosaints.cmavidya.CMAVidyaApp;
import com.sudosaints.cmavidya.R;
import com.sudosaints.cmavidya.dto.CreateNewThreadDTO;
import com.sudosaints.cmavidya.dto.FixedTestDTO;
import com.sudosaints.cmavidya.dto.PostPoneByDayDTO;
import com.sudosaints.cmavidya.dto.PrepareTestDTO;
import com.sudosaints.cmavidya.dto.PreponePostponeDTO;
import com.sudosaints.cmavidya.dto.PreviousTestDTO;
import com.sudosaints.cmavidya.dto.RegisterUserDTO;
import com.sudosaints.cmavidya.dto.TemplateTestDTO;
import com.sudosaints.cmavidya.dto.UpdateAnswer;
import com.sudosaints.cmavidya.dto.UpdateThreadDTO;
import com.sudosaints.cmavidya.dto.UserEventDTO;
import com.sudosaints.cmavidya.model.PlanEvents;
import com.sudosaints.cmavidya.model.User;
import com.sudosaints.cmavidya.util.Logger;

import org.codehaus.jackson.map.ObjectMapper;

import java.net.HttpRetryException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Vishal on 7/30/2014.
 */
public class ApiRequestHelper {

	private static ApiRequestHelper instance;
	private CMAVidyaService cmaVidyaService;
	private CMAVidyaApp application;

	public static synchronized ApiRequestHelper init(CMAVidyaApp application) {
		if (null == instance) {
			instance = new ApiRequestHelper();
			instance.setApplication(application);
			instance.createRestAdapter();
		}
		return instance;
	}

	public boolean checkNetwork() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) instance.application.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/**
	 * Add all the api request's here
	 */

	public void getKeyNotesData(final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getKeyNotes(new Callback<Object>() {

				@Override
				public void success(Object object, Response response) {
//                onRequestComplete.onSuccess((ApiResponse) object);

					Map<String, Object> objectMap = (Map<String, Object>) object;

					if (null != objectMap) {
						if (null != objectMap.get("success"))
							if ((Boolean.parseBoolean(objectMap.get("success").toString()))) {
								apiResponse.setData(objectMap.get("UserNotesList"));
								apiResponse.setSuccess(true);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "server response was not in correct format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));

				}
			});
	}

	public void getAllUsers(final OnRequestComplete onRequestComplete) {


		cmaVidyaService.getAllUsers(new Callback<ApiResponse>() {

			@Override
			public void success(ApiResponse apiResponse, Response response) {
				if (apiResponse.isSuccess()) {
					//Do all the parsing here & pass the desired object.
					// onRequestComplete.onSuccess(new ArrayList<PlannerItem>());
				} else {
					//
					onRequestComplete.onFailure(apiResponse);
				}
			}

			@Override
			public void failure(RetrofitError error) {
				onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError(error.getResponse().getStatus() + "", error.getLocalizedMessage())));
			}
		});
	}

	public void getSubjectListForTest(String username, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getSubjectListForTest(username, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap)
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("200", objectMap.get("message") + ""));
							onRequestComplete.onFailure(apiResponse);
						}
					else
						onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError("0", ApiResponse.ApiError.COMM_ERROR)));

				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError(error.getResponse() + "", error.getLocalizedMessage())));
				}
			});
	}

	public void getTopicForTest(String username, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getTopicListForTest(username, new Callback<Object>() {
						@Override
						public void success(Object o, Response response) {
							Map<String, Object> objectMap = (Map<String, Object>) o;
							if (null != objectMap) {
								if (Boolean.parseBoolean(objectMap.get("success") + "")) {
									apiResponse.setSuccess(true);
									apiResponse.setData(objectMap);
									onRequestComplete.onSuccess(apiResponse);
								} else {
									apiResponse.setSuccess(false);
									apiResponse.setError(new ApiResponse.ApiError("200", objectMap.get("message") + ""));
									onRequestComplete.onFailure(apiResponse);
								}
							} else
								onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError("0", ApiResponse.ApiError.COMM_ERROR)));

						}

						@Override
						public void failure(RetrofitError error) {
							onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError(error.getResponse().getStatus() + "", error.getLocalizedMessage())));
						}
					}

			);
	}

	public void generateTopicTest(PrepareTestDTO prepareTestDTO, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.generateTopicTest(prepareTestDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap)
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("200", objectMap.get("message") + ""));
							onRequestComplete.onFailure(apiResponse);
						}
					else
						onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError("0", ApiResponse.ApiError.COMM_ERROR)));
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError(error.getResponse().getStatus() + "", error.getLocalizedMessage())));
				}
			});
	}

	public void generateSubjectTest(PrepareTestDTO prepareTestDTO, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.generateSubjectTest(prepareTestDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap)
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("200", objectMap.get("message") + ""));
							onRequestComplete.onFailure(apiResponse);
						}
					else
						onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError("0", ApiResponse.ApiError.COMM_ERROR)));
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError(error.getResponse().getStatus() + "", error.getLocalizedMessage())));
				}
			});
	}

	public void getSubjectReplanData(String userEmail, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getSubjectReplanData(userEmail, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					apiResponse.setData(o);
					onRequestComplete.onSuccess(apiResponse);
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError(error.getResponse() + "", error.getLocalizedMessage())));
				}
			});

	}

	public void getTopicReplanData(String userEmail, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getTopicReplanData(userEmail, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					apiResponse.setData(o);
					onRequestComplete.onSuccess(apiResponse);
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse().setError(new ApiResponse.ApiError(error.getResponse() + "", error.getLocalizedMessage())));
				}
			});
	}

	public void loadTest(String username, long idTestLogMaster, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.loadTest(username, idTestLogMaster, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setData(objectMap.get("oTest"));
							apiResponse.setSuccess(true);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}

					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "server response was not in correct format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});

	}

	public void getUserPlannerInfo(String userName, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getUserPlannerInfo(userName, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (null != objectMap.get("success"))
							if ((Boolean.parseBoolean(objectMap.get("success").toString()))) {
								apiResponse.setData(objectMap.get("planinputs"));
								apiResponse.setSuccess(true);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "server response was not in correct format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));

				}
			});
	}

	public void getAllCourss(final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getCourses(new Callback<Object>() {
				@Override
				public void success(Object object, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) object;
					if (null != objectMap) {
						if (null != objectMap.get("success"))
							if ((Boolean.parseBoolean(objectMap.get("success").toString()))) {
								apiResponse.setData(objectMap.get("oList"));
								apiResponse.setSuccess(true);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "server response was not in correct format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError retrofitError) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});

	}

	public void registerUser(final OnRequestComplete onRequestComplete, User user) {

		RegisterUserDTO registerUserDTO = new RegisterUserDTO(user);
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else

			cmaVidyaService.registerUser(registerUserDTO, new Callback<Object>() {
				@Override
				public void success(Object object, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) object;
					if (null != objectMap) {
						if (null != objectMap.get("success"))
							if ((Boolean.parseBoolean(objectMap.get("success").toString()))) {
								apiResponse.setData(objectMap);
								apiResponse.setSuccess(true);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "server response was not in correct format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void loginUser(final OnRequestComplete onRequestComplete, User user) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.loginUser(user.getEmail(), user.getPassword(), new Callback<Object>() {
				@Override
				public void success(Object object, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) object;
					if (null != objectMap) {
						if (null != objectMap.get("success")) {
							if (Boolean.parseBoolean(objectMap.get("success").toString())) {
								apiResponse.setData(objectMap.get("User"));
								apiResponse.setSuccess(true);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								apiResponse.setSuccess(false);
								onRequestComplete.onFailure(apiResponse);
							}
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "server response was not in correct format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void getMyPlanDetails(final OnRequestComplete onRequestComplete, String userName) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getMyPlanDetails(userName, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (null != objectMap.get("success")) {
							if (Boolean.parseBoolean(objectMap.get("success").toString())) {
								apiResponse.setSuccess(true);
								apiResponse.setData(objectMap.get("plans"));
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "server response was not in correct format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void updateSubjectReplanData(Object o, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.updateSubjectReplanData(o, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					apiResponse.setData(o);
					onRequestComplete.onSuccess(apiResponse);
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));

				}
			});
	}

	public void updateTopicReplanData(Object o, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.updateTopicReplanData(o, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					apiResponse.setData(o);
					onRequestComplete.onSuccess(apiResponse);
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));

				}
			});
	}

	public void postPrePonePostPonePlanEvent(final OnRequestComplete onRequestComplete, String userName, int hours, String type, PlanEvents planEvents) {
		PreponePostponeDTO preponePostponeDTO = new PreponePostponeDTO();
		preponePostponeDTO.setUserEmailId(userName);
		preponePostponeDTO.setIdPlannerOutputDateWiseTime(planEvents.getIdPlannerOutPutDateWiseTime());
		preponePostponeDTO.setIdsubjectnamesaketopicmapping(planEvents.getIdTopic());
		preponePostponeDTO.setHours(hours);
		preponePostponeDTO.setRevindex(planEvents.getRevisionNumber());
		preponePostponeDTO.setType(type);
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.postPerponePostPonePlanEvent(preponePostponeDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;

					if (null != objectMap) {
						if (null != objectMap.get("success")) {
							if (Boolean.parseBoolean(objectMap.get("success").toString())) {
								apiResponse.setSuccess(true);
								apiResponse.setData(objectMap);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void postponeByDate(String userName, long idPlannerOutputDateWiseTime, final OnRequestComplete onRequestComplete) {
		PostPoneByDayDTO postPoneByDayDTO = new PostPoneByDayDTO();
		postPoneByDayDTO.setUserEmailId(userName);
		postPoneByDayDTO.setIdPlannerOutputDateWiseTime(idPlannerOutputDateWiseTime);
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.postponeByDay(postPoneByDayDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;

					if (null != objectMap) {
						if (null != objectMap.get("success")) {
							if (Boolean.parseBoolean(objectMap.get("success").toString())) {
								apiResponse.setSuccess(true);
								apiResponse.setData(objectMap);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError retrofitError) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});

	}

	public void updateAnswer(UpdateAnswer updateAnswer, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.updateAnswer(updateAnswer, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void getMasterforumTopics(int id, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getMasterForumTopics(id, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void getForumSubjectFromMasterFourmTopic(int masterForumId, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getForumSubjectByForumMasterId(masterForumId, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void getForumTopics(int forumSubjectId, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getForumBySubjectId(forumSubjectId, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);

						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void getForumThreadByForumId(long forumId, int startRow, int maxRows, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getForumThreadsByForumId(forumId, startRow, maxRows, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);

						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void createNewThreadPost(CreateNewThreadDTO createNewThreadDTO, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.insertPost(createNewThreadDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);

						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void getForumThreads(long threadId, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.getForumThread(threadId, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);

						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void closeThread(long threadId, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.closeThread(threadId, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);

						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void updateThread(UpdateThreadDTO updateThreadDTO, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else
			cmaVidyaService.updatePost(updateThreadDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);

						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
	}

	public void getAllPreviousTest(String username, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			cmaVidyaService.getAllPreviousTestList(username, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});

		}
	}

	public void getTemplateTestList(String username, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			cmaVidyaService.getTemplateTestList(username, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});

		}
	}

	public void getFixedTestList(String username, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			cmaVidyaService.getFixedTestList(username, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});

		}
	}

	public void getAllTestDependingOnIsTestEnd(String username, boolean isEnd, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			cmaVidyaService.getTestDependingOnIsTestEnd(username, isEnd, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
		}
	}

	public void genrateTemplateTest(TemplateTestDTO templateTestDTO, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			if (templateTestDTO.getIdTestType() == 1) {
				cmaVidyaService.genrateMcqTemplateTest(templateTestDTO, new Callback<Object>() {
					@Override
					public void success(Object o, Response response) {
						Map<String, Object> objectMap = (Map<String, Object>) o;
						if (null != objectMap) {
							if (Boolean.parseBoolean(objectMap.get("success") + "")) {
								apiResponse.setSuccess(true);
								apiResponse.setData(objectMap);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
							onRequestComplete.onFailure(apiResponse);
						}
					}

					@Override
					public void failure(RetrofitError error) {
						onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
					}
				});
			} else {
				cmaVidyaService.genrateTheoryTemplateTest(templateTestDTO, new Callback<Object>() {
					@Override
					public void success(Object o, Response response) {
						Map<String, Object> objectMap = (Map<String, Object>) o;
						if (null != objectMap) {
							if (Boolean.parseBoolean(objectMap.get("success") + "")) {
								apiResponse.setSuccess(true);
								apiResponse.setData(objectMap);
								onRequestComplete.onSuccess(apiResponse);
							} else {
								apiResponse.setSuccess(false);
								apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
								onRequestComplete.onFailure(apiResponse);
							}
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
							onRequestComplete.onFailure(apiResponse);
						}
					}

					@Override
					public void failure(RetrofitError error) {
						onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
					}
				});
			}
		}
	}

	public void generatePreviousTest(PreviousTestDTO previousTestDTO, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			cmaVidyaService.generatePreviousTest(previousTestDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
		}
	}

	public void generateFixedTest(FixedTestDTO fixedTestDTO, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			cmaVidyaService.generateFixedTest(fixedTestDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
		}
	}

	public void getUserVideos(String userId, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			cmaVidyaService.getUserVideos(userId, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
		}
	}

	public void createDeleteUserEvent(UserEventDTO userEventDTO, final OnRequestComplete onRequestComplete) {
		final ApiResponse apiResponse = new ApiResponse();
		if (!checkNetwork()) {
			apiResponse.setSuccess(false);
			apiResponse.setError(new ApiResponse.ApiError("0", "No Active Internet Connection Detected"));
			onRequestComplete.onFailure(apiResponse);
		} else {
			cmaVidyaService.createDeleteUserEvents(userEventDTO, new Callback<Object>() {
				@Override
				public void success(Object o, Response response) {
					Map<String, Object> objectMap = (Map<String, Object>) o;
					if (null != objectMap) {
						if (Boolean.parseBoolean(objectMap.get("success") + "")) {
							apiResponse.setSuccess(true);
							apiResponse.setData(objectMap);
							onRequestComplete.onSuccess(apiResponse);
						} else {
							apiResponse.setSuccess(false);
							apiResponse.setError(new ApiResponse.ApiError("1", objectMap.get("message").toString()));
							onRequestComplete.onFailure(apiResponse);
						}
					} else {
						apiResponse.setSuccess(false);
						apiResponse.setError(new ApiResponse.ApiError("0", "Server responce in bad format"));
						onRequestComplete.onFailure(apiResponse);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					onRequestComplete.onFailure(new ApiResponse(false, null, "Server responce not in format"));
				}
			});
		}
	}

	/**
	 * REST Adapter Configuration
	 */
	private void createRestAdapter() {
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setReadTimeout(120 * 1000, TimeUnit.MILLISECONDS);
		ObjectMapper objectMapper = new ObjectMapper();
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setConverter(new JacksonConverter(objectMapper))
				.setErrorHandler(new ErrorHandler() {
					@Override
					public Throwable handleError(RetrofitError error) {
						application.getLogger().debug("REST Error");
						final Response response = error.getResponse();
						if (response != null) {
							int statusCode = response.getStatus();
							if (error.isNetworkError() || (500 <= statusCode && statusCode < 600)) {
								return new HttpRetryException(Logger.tag, statusCode);
							}
						}
						return error;
					}
				})
				.setClient(new OkClient(okHttpClient))
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.setLog(new AndroidLog(Logger.tag))
				.setEndpoint(application.getResources().getString(R.string.server_url))
				.setRequestInterceptor(getRequestInterceptor())
				.build();

		cmaVidyaService = restAdapter.create(CMAVidyaService.class);
	}


	/**
	 * End api requests
	 */

	private RequestInterceptor getRequestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void intercept(RequestFacade request) {
				//Add Headers here
				request.addHeader("Accept", "application/json");
			}
		};
	}

	/**
	 * End REST Adapter Configuration
	 */

	public void setCmaVidyaService(CMAVidyaService cmaVidyaService) {
		this.cmaVidyaService = cmaVidyaService;
	}

	public CMAVidyaApp getApplication() {
		return application;
	}

	public void setApplication(CMAVidyaApp application) {
		this.application = application;
	}

	public static interface OnRequestComplete {
		public void onSuccess(ApiResponse apiResponse);

		public void onFailure(ApiResponse apiResponse);
	}
}
