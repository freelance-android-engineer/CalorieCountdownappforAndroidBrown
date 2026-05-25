package ese.com.caloriecountdownappforandroidbrown.ui.debitactivitycif13;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ese.com.caloriecountdownappforandroidbrown.CCD_GUI_CD_CIF1;
import ese.com.caloriecountdownappforandroidbrown.Fitness_Item_CIF5;
import ese.com.caloriecountdownappforandroidbrown.R;
import ese.com.caloriecountdownappforandroidbrown.RoundingCIF13;
import ese.com.caloriecountdownappforandroidbrown.SummaryBoxCIF12;

public class Debit_Activity_CIF13Fragment extends Fragment {

    private DebitActivityCIF13ViewModel mViewModel;
    public static final String TOTAL_DEBIT_VALUE = "Total Debit Countdown Value";

    private Button mDebit;
    private Button mCancel;
    private Fitness_Item_CIF5 mCountdown;

    public static Debit_Activity_CIF13Fragment newInstance() {
        return new Debit_Activity_CIF13Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.debit__activity__c_i_f13_fragment, container, false);

        mDebit  = v.findViewById(R.id.button12);
        mCancel = v.findViewById(R.id.button13);

        mDebit.setOnClickListener(view -> {
            try {
                EditText editText63 = v.findViewById(R.id.edit_text63);
                EditText editText64 = v.findViewById(R.id.edit_text64);
                Spinner  spinner    = v.findViewById(R.id.spincity);

                // Guard: weight field
                if (editText63 == null || editText63.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter your weight in lbs.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Guard: minutes/reps field
                if (editText64 == null || editText64.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter minutes or reps performed.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Guard: spinner selection
                if (spinner == null || spinner.getSelectedItem() == null) {
                    Toast.makeText(requireContext(), "Please select an activity.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mCountdown = new Fitness_Item_CIF5();
                mCountdown.setmUserWeightlbs(
                        new RoundingCIF13().StringToFloat(editText63.getText().toString().trim()));
                mCountdown.setmMinutesPerformed(
                        (int) new RoundingCIF13().StringToFloat(editText64.getText().toString().trim()));
                mCountdown.ConvertSpinnerItem(spinner.getSelectedItem().toString());

                BackToParent(GetCountdownDebit(mCountdown));

            } catch (NumberFormatException e) {
                android.util.Log.e("DebitCIF13", "NFE: " + e.getMessage(), e);
                Toast.makeText(requireContext(),
                        "Invalid number entered. Please check weight and minutes fields.",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                android.util.Log.e("DebitCIF13", "Debit error: " + e.getMessage(), e);
                Toast.makeText(requireContext(),
                        "Error processing debit: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        mCancel.setOnClickListener(view -> {
            if (getActivity() != null) {
                getActivity().setResult(AppCompatActivity.RESULT_CANCELED);
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DebitActivityCIF13ViewModel.class);
    }

    private int GetCountdownDebit(Fitness_Item_CIF5 fizz) {
        int debit = fizz.CalculateCountdown();
        SummaryBoxCIF12 summy = SummaryBoxCIF12.get(requireActivity());
        summy.Set_mFitnessItems(fizz);
        return debit;
    }

    private void BackToParent(int debit) {
        if (getActivity() == null) return;
        Intent i2 = new Intent();
        i2.putExtra(TOTAL_DEBIT_VALUE, debit);
        getActivity().setResult(AppCompatActivity.RESULT_OK, i2);
        getActivity().finish();
    }
}
