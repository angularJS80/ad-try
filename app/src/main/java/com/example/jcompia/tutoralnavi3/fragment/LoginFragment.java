package com.example.jcompia.tutoralnavi3.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jcompia.tutoralnavi3.FragmentActivity;
import com.example.jcompia.tutoralnavi3.MainActivity;
import com.example.jcompia.tutoralnavi3.R;
import com.example.jcompia.tutoralnavi3.rest.adapter.CardAdapter;
import com.example.jcompia.tutoralnavi3.rest.service.GithubService;
import com.example.jcompia.tutoralnavi3.rest.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static final String TAG = "LoginFragment";
    private Context mContext;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Toast.makeText(getActivity(), "LoginFragment!",     Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText idText = (EditText) rootView.findViewById(R.id.idText);
        final EditText passwordText = (EditText) rootView.findViewById(R.id.idText);
        final Button loginButton = (Button) rootView.findViewById(R.id.loginButton);
        final TextView registerButton = (TextView) rootView.findViewById(R.id.registerButton);
        final CardAdapter mCardAdapter = new CardAdapter();


        Account account = GenericAccountService.GetAccount();
        AccountManager accountManager = (AccountManager) getContext().getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccountsByType("com.example.jcompia.tutoralnavi3.AccountType");
        Log.e("Loginfragment accounts", ""+accounts.length);

        if(accounts.length>0){
            account = accounts[0];
        }
        Account finalAccount = account;

        GithubService service = ServiceFactory.createRetrofitService(GithubService.class, GithubService.SERVICE_ENDPOINT, MainActivity.mContext,finalAccount); // Url 이 설정되어있고 호출할수있는 메소드가 정의된 서비를 정의한다.



        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Map<String,String> parameters = new HashMap<>();
                parameters.put("username",idText.getText().toString());
                parameters.put("password",passwordText.getText().toString());
                //Github github = new Github();
                service.authenticateUser(parameters) // 서비스의 결과는 옵저버를을 반환 함으로 이후의 처리는 옵저버블형태이다.
                .subscribeOn(Schedulers.newThread())

                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Map>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("Disposable", d.toString());
                    }

                    @Override
                    public void onNext(Map map) {
                        Log.e("GithubDemo", map.toString());
                        Log.e("GithubDemo", map.get("token").toString());
                        //계정이 이미 있다고 해서.. 한번 넣고 끝인가.
                        accountManager.addAccountExplicitly(finalAccount, null, null);
                        accountManager.setAuthToken(finalAccount,"full_access",map.get("token").toString());
                        ((FragmentActivity)getActivity()).switchFragment(new DashboardFragment());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("GithubDemo", e.getMessage());
                    }

                    @Override
                    public void onComplete() {}

                });
            }

        });
/*

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for(String login : Data.githubList) {
                    service.getHeroList()
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Github>() {
                                @Override
                                public void onSubscribe(Disposable d) {}

                                @Override
                                public void onNext(Github github) {
                                    mCardAdapter.addData(github);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("GithubDemo", e.getMessage());
                                }

                                @Override
                                public void onComplete() {}

                            });
                }
            }

        });
*/


        return rootView;

        //return inflater.inflate(R.layout.fragment_login, container, false);
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
    /*    super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/

        super.onAttach(context);
        mContext = context;
        /*if (context instanceof Activity){
            a=(Activity) context;
        }*/

    }

    /*
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
