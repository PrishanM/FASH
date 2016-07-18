package com.evensel.android.fash.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.evensel.android.fash.R;
import com.evensel.android.fash.SingleOrderItemActivity;
import com.evensel.android.fash.adapters.OrderListAdapter;

/**
 * Created by Prishan Maduka on 7/15/2016.
 */
public class OrderHistoryFragment extends Fragment {

    ListView orderList;
    View rootView;
    OrderListAdapter orderListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_history_layout, container, false);

        initialize();

        return rootView;
    }

    private void initialize() {
        orderList = (ListView)rootView.findViewById(R.id.listOrders);
        orderListAdapter = new OrderListAdapter(getActivity());
        orderList.setAdapter(orderListAdapter);

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SingleOrderItemActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
