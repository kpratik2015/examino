

package com.ibm.mobileappbuilder.examino20160923051552.ds;

import android.content.Context;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.restds.AppNowDatasource;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.ds.restds.TypedByteArrayUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * "QuestionsDS" data source. (e37eb8dc-6eb2-4635-8592-5eb9696050e3)
 */
public class QuestionsDS extends AppNowDatasource<QuestionsDSItem>{

    // default page size
    private static final int PAGE_SIZE = 20;

    private QuestionsDSService service;

    public static QuestionsDS getInstance(SearchOptions searchOptions){
        return new QuestionsDS(searchOptions);
    }

    private QuestionsDS(SearchOptions searchOptions) {
        super(searchOptions);
        this.service = QuestionsDSService.getInstance();
    }

    @Override
    public void getItem(String id, final Listener<QuestionsDSItem> listener) {
        if ("0".equals(id)) {
                        getItems(new Listener<List<QuestionsDSItem>>() {
                @Override
                public void onSuccess(List<QuestionsDSItem> items) {
                    if(items != null && items.size() > 0) {
                        listener.onSuccess(items.get(0));
                    } else {
                        listener.onSuccess(new QuestionsDSItem());
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure(e);
                }
            });
        } else {
                      service.getServiceProxy().getQuestionsDSItemById(id, new Callback<QuestionsDSItem>() {
                @Override
                public void success(QuestionsDSItem result, Response response) {
                                        listener.onSuccess(result);
                }

                @Override
                public void failure(RetrofitError error) {
                                        listener.onFailure(error);
                }
            });
        }
    }

    @Override
    public void getItems(final Listener<List<QuestionsDSItem>> listener) {
        getItems(0, listener);
    }

    @Override
    public void getItems(int pagenum, final Listener<List<QuestionsDSItem>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
        int skipNum = pagenum * PAGE_SIZE;
        String skip = skipNum == 0 ? null : String.valueOf(skipNum);
        String limit = PAGE_SIZE == 0 ? null: String.valueOf(PAGE_SIZE);
        String sort = getSort(searchOptions);
                service.getServiceProxy().queryQuestionsDSItem(
                skip,
                limit,
                conditions,
                sort,
                null,
                null,
                new Callback<List<QuestionsDSItem>>() {
            @Override
            public void success(List<QuestionsDSItem> result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    private String[] getSearchableFields() {
        return new String[]{"subject", "type", "questionType", "question"};
    }

    // Pagination

    @Override
    public int getPageSize(){
        return PAGE_SIZE;
    }

    @Override
    public void getUniqueValuesFor(String searchStr, final Listener<List<String>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
                service.getServiceProxy().distinct(searchStr, conditions, new Callback<List<String>>() {
             @Override
             public void success(List<String> result, Response response) {
                                  result.removeAll(Collections.<String>singleton(null));
                 listener.onSuccess(result);
             }

             @Override
             public void failure(RetrofitError error) {
                                  listener.onFailure(error);
             }
        });
    }

    @Override
    public URL getImageUrl(String path) {
        return service.getImageUrl(path);
    }

    @Override
    public void create(QuestionsDSItem item, Listener<QuestionsDSItem> listener) {
                    
        if(item.figureUri != null){
            service.getServiceProxy().createQuestionsDSItem(item,
                TypedByteArrayUtils.fromUri(item.figureUri),
                callbackFor(listener));
        }
        else
            service.getServiceProxy().createQuestionsDSItem(item, callbackFor(listener));
        
    }

    private Callback<QuestionsDSItem> callbackFor(final Listener<QuestionsDSItem> listener) {
      return new Callback<QuestionsDSItem>() {
          @Override
          public void success(QuestionsDSItem item, Response response) {
                            listener.onSuccess(item);
          }

          @Override
          public void failure(RetrofitError error) {
                            listener.onFailure(error);
          }
      };
    }

    @Override
    public void updateItem(QuestionsDSItem item, Listener<QuestionsDSItem> listener) {
                    
        if(item.figureUri != null){
            service.getServiceProxy().updateQuestionsDSItem(item.getIdentifiableId(),
                item,
                TypedByteArrayUtils.fromUri(item.figureUri),
                callbackFor(listener));
        }
        else
            service.getServiceProxy().updateQuestionsDSItem(item.getIdentifiableId(), item, callbackFor(listener));
        
    }

    @Override
    public void deleteItem(QuestionsDSItem item, final Listener<QuestionsDSItem> listener) {
                service.getServiceProxy().deleteQuestionsDSItemById(item.getIdentifiableId(), new Callback<QuestionsDSItem>() {
            @Override
            public void success(QuestionsDSItem result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    @Override
    public void deleteItems(List<QuestionsDSItem> items, final Listener<QuestionsDSItem> listener) {
                service.getServiceProxy().deleteByIds(collectIds(items), new Callback<List<QuestionsDSItem>>() {
            @Override
            public void success(List<QuestionsDSItem> item, Response response) {
                                listener.onSuccess(null);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    protected List<String> collectIds(List<QuestionsDSItem> items){
        List<String> ids = new ArrayList<>();
        for(QuestionsDSItem item: items){
            ids.add(item.getIdentifiableId());
        }
        return ids;
    }

}

