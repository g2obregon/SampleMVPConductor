package com.sample.mvp.conductor.controller.home;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.sample.mvp.conductor.R;

import com.sample.mvp.conductor.base.BaseController;
import com.sample.mvp.conductor.controller.second.SecondController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeController extends BaseController implements HomeView {

    private static final String KEY_FAB_VISIBILITY = "HomeController.fabVisibility";

    private HomePresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public HomeController() {
        //setHasOptionsMenu(true);
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_home, container, false);
    }


    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new HomeAdapter(LayoutInflater.from(view.getContext())));

        presenter = new HomePresenterImpl(this);
        presenter.loadItems();
    }

    @Override
    protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
    }



    @Override
    protected void onChangeStarted(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        setOptionsMenuHidden(!changeType.isEnter);

        if (changeType.isEnter) {
            setTitle();
        }
    }

    @Override
    public void onItemsLoadFinished(HomeModel[] homeModel) {
        HomeAdapter adapter = (HomeAdapter) recyclerView.getAdapter();
        adapter.setItems(homeModel);
        adapter.notifyDataSetChanged();
    }



    void onModelRowClick(HomeModel model, int position) {
        switch (model) {
            case CHILD_CONTROLLERS:
                getRouter().pushController(RouterTransaction.with(new SecondController())
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler()));
                break;
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

        private final LayoutInflater inflater;
        private HomeModel[] items;

        public HomeAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
            this.items = new HomeModel[]{};
        }

        public void setItems(HomeModel[] items){
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.row_home, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(position, items[position]);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_title) TextView tvTitle;
            @BindView(R.id.img_dot)
            ImageView imgDot;
            private HomeModel model;
            private int position;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                /*if(tvTitle == null){
                    tvTitle = itemView.findViewById(R.id.tv_title);
                    imgDot = itemView.findViewById(R.id.img_dot);
                    itemView.findViewById(R.id.row_root).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onModelRowClick(model, position);
                        }
                    });
                }*/
            }

            void bind(int position, HomeModel item) {
                model = item;
                tvTitle.setText(item.title);
                imgDot.getDrawable().setColorFilter(ContextCompat.getColor(getActivity(), item.color), PorterDuff.Mode.SRC_ATOP);
                this.position = position;

                ViewCompat.setTransitionName(tvTitle, getResources().getString(R.string.transition_tag_title_indexed, position));
                ViewCompat.setTransitionName(imgDot, getResources().getString(R.string.transition_tag_dot_indexed, position));
            }

            @OnClick(R.id.row_root)
            void onRowClick() {
                onModelRowClick(model, position);
            }

        }
    }

    @Override
    protected String getTitle() {
        return "Simple MVP with Conductor";
    }
}
