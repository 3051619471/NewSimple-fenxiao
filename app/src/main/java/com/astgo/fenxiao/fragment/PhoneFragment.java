//package com.astgo.fenxiao.fragment;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.astgo.fenxiao.R;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class PhoneFragment extends Fragment implements View.OnClickListener {
//    private TextView tv_call_log,tv_contacts;
//    private FragmentManager manager = null;
//    private CallsFragment callsFragment;
//    private ContactsFragment contactsFragment;
//
//    public PhoneFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_phone, container, false);
//        initView(rootView);
//        return rootView;
//    }
//
//    private void initView(View rootView) {
//        tv_call_log = (TextView) rootView.findViewById(R.id.tv_call_log);
//        tv_contacts = (TextView) rootView.findViewById(R.id.tv_contacts);
//
//        tv_call_log.setOnClickListener(this);
//        tv_contacts.setOnClickListener(this);
//
//        //设置默认
//        setDefaultFragment();
//    }
//
//    private void setDefaultFragment() {
//        manager = getFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        callsFragment = new CallsFragment();
//        transaction.add(R.id.ll_content,callsFragment);
//        transaction.commit();
//
//        tv_call_log.setSelected(true);
//        tv_contacts.setSelected(false);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            imm.hideSoftInputFromWindow(getActivity().findViewById(R.id.root).getWindowToken(), 0);
//        }
//
//        switch (v.getId()){
//            case R.id.tv_call_log:
//
//                if (!tv_call_log.isSelected()){
//                    tv_call_log.setSelected(true);
//                    tv_contacts.setSelected(false);
//                }
//                FragmentTransaction transaction = manager.beginTransaction();
//                if (callsFragment!=null){
//                   if ( callsFragment.isAdded()){
//                       if (contactsFragment!=null)transaction.hide(contactsFragment);
//                       transaction.show(callsFragment);
//                       transaction.commit();
//                   }
//                }
//
//
//                break;
//            case R.id.tv_contacts:
//                if (!tv_contacts.isSelected()){
//                    tv_contacts.setSelected(true);
//                    tv_call_log.setSelected(false);
//                }
//
//                FragmentTransaction transaction1 = manager.beginTransaction();
//
//                if (contactsFragment == null){
//                    contactsFragment = new ContactsFragment();
//                    if (callsFragment!=null)transaction1.hide(callsFragment);
//                    transaction1.add(R.id.ll_content,contactsFragment);
//                    transaction1.commit();
//                }else {
//                    if (contactsFragment.isAdded()){
//                        if (callsFragment!=null)transaction1.hide(callsFragment);
//                        transaction1.show(contactsFragment);
//                        transaction1.commit();
//                    }
//                }
//
//
//                break;
//        }
//    }
//}
