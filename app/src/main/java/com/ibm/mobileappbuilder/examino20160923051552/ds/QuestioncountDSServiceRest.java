
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

public interface QuestioncountDSServiceRest{

	@GET("/app/57e4bbdee6c8040300ef29af/r/questioncountDS")
	void queryQuestioncountDSItem(
		@Query("skip") String skip,
		@Query("limit") String limit,
		@Query("conditions") String conditions,
		@Query("sort") String sort,
		@Query("select") String select,
		@Query("populate") String populate,
		Callback<List<QuestioncountDSItem>> cb);

	@GET("/app/57e4bbdee6c8040300ef29af/r/questioncountDS/{id}")
	void getQuestioncountDSItemById(@Path("id") String id, Callback<QuestioncountDSItem> cb);

	@DELETE("/app/57e4bbdee6c8040300ef29af/r/questioncountDS/{id}")
  void deleteQuestioncountDSItemById(@Path("id") String id, Callback<QuestioncountDSItem> cb);

  @POST("/app/57e4bbdee6c8040300ef29af/r/questioncountDS/deleteByIds")
  void deleteByIds(@Body List<String> ids, Callback<List<QuestioncountDSItem>> cb);

  @POST("/app/57e4bbdee6c8040300ef29af/r/questioncountDS")
  void createQuestioncountDSItem(@Body QuestioncountDSItem item, Callback<QuestioncountDSItem> cb);

  @PUT("/app/57e4bbdee6c8040300ef29af/r/questioncountDS/{id}")
  void updateQuestioncountDSItem(@Path("id") String id, @Body QuestioncountDSItem item, Callback<QuestioncountDSItem> cb);

  @GET("/app/57e4bbdee6c8040300ef29af/r/questioncountDS")
  void distinct(
        @Query("distinct") String colName,
        @Query("conditions") String conditions,
        Callback<List<String>> cb);
}

