

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
 * "QuestioncountDS" data source. (e37eb8dc-6eb2-4635-8592-5eb9696050e3)
 */
public class QuestioncountDS extends AppNowDatasource<QuestioncountDSItem>{

    // default page size
    private static final int PAGE_SIZE = 20;

    private QuestioncountDSService service;

    public static QuestioncountDS getInstance(SearchOptions searchOptions){
        return new QuestioncountDS(searchOptions);
    }

    private QuestioncountDS(SearchOptions searchOptions) {
        super(searchOptions);
        this.service = QuestioncountDSService.getInstance();
    }

    @Override
    public void getItem(String id, final Listener<QuestioncountDSItem> listener) {
        if ("0".equals(id)) {
                        getItems(new Listener<List<QuestioncountDSItem>>() {
                @Override
                public void onSuccess(List<QuestioncountDSItem> items) {
                    if(items != null && items.size() > 0) {
                        listener.onSuccess(items.get(0));
                    } else {
                        listener.onSuccess(new QuestioncountDSItem());
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure(e);
                }
            });
        } else {
                      service.getServiceProxy().getQuestioncountDSItemById(id, new Callback<QuestioncountDSItem>() {
                @Override
                public void success(QuestioncountDSItem result, Response response) {
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
    public void getItems(final Listener<List<QuestioncountDSItem>> listener) {
        getItems(0, listener);
    }

    @Override
    public void getItems(int pagenum, final Listener<List<QuestioncountDSItem>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
        int skipNum = pagenum * PAGE_SIZE;
        String skip = skipNum == 0 ? null : String.valueOf(skipNum);
        String limit = PAGE_SIZE == 0 ? null: String.valueOf(PAGE_SIZE);
        String sort = getSort(searchOptions);
                service.getServiceProxy().queryQuestioncountDSItem(
                skip,
                limit,
                conditions,
                sort,
                null,
                null,
                new Callback<List<QuestioncountDSItem>>() {
            @Override
            public void success(List<QuestioncountDSItem> result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    private String[] getSearchableFields() {
        return new String[]{"subject"};
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
    public void create(QuestioncountDSItem item, Listener<QuestioncountDSItem> listener) {
                          service.getServiceProxy().createQuestioncountDSItem(item, callbackFor(listener));
          }

    private Callback<QuestioncountDSItem> callbackFor(final Listener<QuestioncountDSItem> listener) {
      return new Callback<QuestioncountDSItem>() {
          @Override
          public void success(QuestioncountDSItem item, Response response) {
                            listener.onSuccess(item);
          }

          @Override
          public void failure(RetrofitError error) {
                            listener.onFailure(error);
          }
      };
    }

    @Override
    public void updateItem(QuestioncountDSItem item, Listener<QuestioncountDSItem> listener) {
                          service.getServiceProxy().updateQuestioncountDSItem(item.getIdentifiableId(), item, callbackFor(listener));
          }

    @Override
    public void deleteItem(QuestioncountDSItem item, final Listener<QuestioncountDSItem> listener) {
                service.getServiceProxy().deleteQuestioncountDSItemById(item.getIdentifiableId(), new Callback<QuestioncountDSItem>() {
            @Override
            public void success(QuestioncountDSItem result, Response response) {
                                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    @Override
    public void deleteItems(List<QuestioncountDSItem> items, final Listener<QuestioncountDSItem> listener) {
                service.getServiceProxy().deleteByIds(collectIds(items), new Callback<List<QuestioncountDSItem>>() {
            @Override
            public void success(List<QuestioncountDSItem> item, Response response) {
                                listener.onSuccess(null);
            }

            @Override
            public void failure(RetrofitError error) {
                                listener.onFailure(error);
            }
        });
    }

    protected List<String> collectIds(List<QuestioncountDSItem> items){
        List<String> ids = new ArrayList<>();
        for(QuestioncountDSItem item: items){
            ids.add(item.getIdentifiableId());
        }
        return ids;
    }

}

