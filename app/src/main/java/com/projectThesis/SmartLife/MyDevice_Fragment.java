package com.projectThesis.SmartLife;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDevice_Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    private Switch switch_view;
    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdaptert;
    private GridViewAdapter gridViewAdapter;
    private List<Product_View> product_viewList;

    private int currentViewMode = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle("my device");

        return inflater.inflate(R.layout.fragment_mydevice, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stubList = (ViewStub) getActivity().findViewById(R.id.stub_list);
        stubGrid = (ViewStub) getActivity().findViewById(R.id.stub_grid);
        switch_view = (Switch) getActivity().findViewById(R.id.switch_view);

        switch_view.setOnCheckedChangeListener(this);

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) getActivity().findViewById(R.id.my_ListView);
        gridView = (GridView) getActivity().findViewById(R.id.my_GridView);

        getProductList();

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("ViewMode", Context.MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);

        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();
    }

    private void switchView() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            stubList.setVisibility(View.VISIBLE);
            stubGrid.setVisibility(View.GONE);
        } else {
            stubList.setVisibility(View.GONE);
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdaptert = new ListViewAdapter(getContext(), R.layout.list_item, product_viewList);
            listView.setAdapter(listViewAdaptert);
        } else {
            gridViewAdapter = new GridViewAdapter(getContext(), R.layout.grid_item, product_viewList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    private List<Product_View> getProductList() {
        product_viewList = new ArrayList<>();
        product_viewList.add(new Product_View(R.drawable.ic_login, "Title 1"));
        product_viewList.add(new Product_View(R.drawable.ic_login, "Title 2"));
        product_viewList.add(new Product_View(R.drawable.ic_login, "Title 3"));
        return product_viewList;
    }

    // On click item
    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getActivity(), product_viewList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // The toggle is enabled
            currentViewMode = VIEW_MODE_GRIDVIEW;
        } else {
            // The toggle is disabled
            currentViewMode = VIEW_MODE_LISTVIEW;
        }

        switchView();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("ViewMode", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("currentViewMode", currentViewMode);
        editor.commit();
    }

}
