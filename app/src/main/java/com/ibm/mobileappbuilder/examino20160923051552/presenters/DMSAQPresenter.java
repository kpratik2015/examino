
package com.ibm.mobileappbuilder.examino20160923051552.presenters;

import com.ibm.mobileappbuilder.examino20160923051552.R;
import com.ibm.mobileappbuilder.examino20160923051552.ds.QuestionsDSItem;

import java.util.List;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.ListCrudPresenter;
import ibmmobileappbuilder.mvp.view.CrudListView;

public class DMSAQPresenter extends BasePresenter implements ListCrudPresenter<QuestionsDSItem>,
      Datasource.Listener<QuestionsDSItem>{

    private final CrudDatasource<QuestionsDSItem> crudDatasource;
    private final CrudListView<QuestionsDSItem> view;

    public DMSAQPresenter(CrudDatasource<QuestionsDSItem> crudDatasource,
                                         CrudListView<QuestionsDSItem> view) {
       this.crudDatasource = crudDatasource;
       this.view = view;
    }

    @Override
    public void deleteItem(QuestionsDSItem item) {
        crudDatasource.deleteItem(item, this);
    }

    @Override
    public void deleteItems(List<QuestionsDSItem> items) {
        crudDatasource.deleteItems(items, this);
    }

    @Override
    public void addForm() {
        view.showAdd();
    }

    @Override
    public void editForm(QuestionsDSItem item, int position) {
        view.showEdit(item, position);
    }

    @Override
    public void detail(QuestionsDSItem item, int position) {
        view.showDetail(item, position);
    }

    @Override
    public void onSuccess(QuestionsDSItem item) {
                view.showMessage(R.string.items_deleted);
        view.refresh();
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic);
    }

}

