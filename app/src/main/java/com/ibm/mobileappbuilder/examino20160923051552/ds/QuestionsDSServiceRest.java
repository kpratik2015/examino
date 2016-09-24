
package com.ibm.mobileappbuilder.examino20160923051552.ds;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.POST;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Path;
import retrofit.http.PUT;
import retrofit.mime.TypedByteArray;
import retrofit.http.Part;
import retrofit.http.Multipart;

public interface QuestionsDSServiceRest{

	@GET("/app/57e4bbdee6c8040300ef29af/r/questionsDS")
	void queryQuestionsDSItem(
		@Query("skip") String skip,
		@Query("limit") String limit,
		@Query("conditions") String conditions,
		@Query("sort") String sort,
		@Query("select") String select,
		@Query("populate") String populate,
		Callback<List<QuestionsDSItem>> cb);

	@GET("/app/57e4bbdee6c8040300ef29af/r/questionsDS/{id}")
	void getQuestionsDSItemById(@Path("id") String id, Callback<QuestionsDSItem> cb);

	@DELETE("/app/57e4bbdee6c8040300ef29af/r/questionsDS/{id}")
  void deleteQuestionsDSItemById(@Path("id") String id, Callback<QuestionsDSItem> cb);

  @POST("/app/57e4bbdee6c8040300ef29af/r/questionsDS/deleteByIds")
  void deleteByIds(@Body List<String> ids, Callback<List<QuestionsDSItem>> cb);

  @POST("/app/57e4bbdee6c8040300ef29af/r/questionsDS")
  void createQuestionsDSItem(@Body QuestionsDSItem item, Callback<QuestionsDSItem> cb);

  @PUT("/app/57e4bbdee6c8040300ef29af/r/questionsDS/{id}")
  void updateQuestionsDSItem(@Path("id") String id, @Body QuestionsDSItem item, Callback<QuestionsDSItem> cb);

  @GET("/app/57e4bbdee6c8040300ef29af/r/questionsDS")
  void distinct(
        @Query("distinct") String colName,
        @Query("conditions") String conditions,
        Callback<List<String>> cb);
    
    @Multipart
    @POST("/app/57e4bbdee6c8040300ef29af/r/questionsDS")
    void createQuestionsDSItem(
        @Part("data") QuestionsDSItem item,
        @Part("figure") TypedByteArray figure,
        Callback<QuestionsDSItem> cb);
    
    @Multipart
    @PUT("/app/57e4bbdee6c8040300ef29af/r/questionsDS/{id}")
    void updateQuestionsDSItem(
        @Path("id") String id,
        @Part("data") QuestionsDSItem item,
        @Part("figure") TypedByteArray figure,
        Callback<QuestionsDSItem> cb);
}

