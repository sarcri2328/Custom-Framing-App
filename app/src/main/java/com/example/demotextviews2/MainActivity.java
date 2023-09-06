package com.example.demotextviews2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSubmit).setOnClickListener( v -> onSubmitClick() );


    }

    @SuppressLint("SetTextI18n")
    private void onSubmitClick() {

        // Gather User input from Image Width field
        EditText tietWidth = findViewById(R.id.tietWidth);
        String widthStr = tietWidth.getText().toString();
        double imageWidth;
        try {
            imageWidth = Double.parseDouble(widthStr);
        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Width input field must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gather User input from Image Height field
        EditText tietHeight = findViewById(R.id.tietHeight);
        String heightStr = tietHeight.getText().toString();
        double imageHeight;
        try {
            imageHeight = Double.parseDouble(heightStr);
        } catch (NumberFormatException ex2) {
            Toast.makeText(this, "Height input field must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gather User input from Mat margin field
        EditText tietMatMargin = findViewById(R.id.tietMatMargin);
        String matMarginStr = tietMatMargin.getText().toString();
        double matDouble;
        try {
            matDouble = Double.parseDouble(matMarginStr);
        } catch (NumberFormatException ex3) {
            Toast.makeText(this, "Mat margin input field must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        double matMarginNeeded = matDouble + matDouble;

        // Gather User input from Frame Price per Foot field
        EditText tietFramePricePerFoot = findViewById(R.id.tietFramePricePerFoot);
        String framePricePerFootStr = tietFramePricePerFoot.getText().toString();
        int framePricePerFoot;
        try {
            framePricePerFoot = Integer.parseInt(framePricePerFootStr);
        } catch (NumberFormatException ex4) {
            Toast.makeText(this, "Frame price input field must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initials and Formulas for calculating frame width and frame height using above variables
        double frameWidth;
        frameWidth = imageWidth + matMarginNeeded;
        double frameHeight;
        frameHeight = imageHeight + matMarginNeeded;

        double unitedInches = (frameWidth + frameHeight) * 2;
        //   double unitedInchesRound = (Math.ceil(unitedInches));

        /* Initialize variable and if statement for frame feetNeeded
         * If the calculation is a whole number, increment by 1.
         * If the calculation is a double, round up to nearest whole number.
         *  */
        double frameFeetNeeded = (unitedInches / 12);
        if (frameFeetNeeded == (int) frameFeetNeeded) {
            frameFeetNeeded = frameFeetNeeded + 1;
        } else {
            frameFeetNeeded = (Math.ceil(unitedInches / 12));
        }

        double totalFramePrice = framePricePerFoot * frameFeetNeeded;
        DecimalFormat df = new DecimalFormat("0.00");

        // Gather User input from Standard Mat Qty field
        int standardMatPrice = 45;
        int oversizedStandardMatPrice = 95;
        EditText tietStandardMatQty = findViewById(R.id.tietStandardMatQty);
        String tietStandardMatQtyStr = tietStandardMatQty.getText().toString();
        int standardMatQty;
        try {
            standardMatQty = Integer.parseInt(tietStandardMatQtyStr);
        } catch (NumberFormatException ex5) {
            Toast.makeText(this, "Standard Mat Qty input field must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gather User input from Textured Mat Qty field
        int texturedMatPrice = 88;
        int oversizedTexturedMatPrice = 165;
        EditText tietTexturedMatQty = findViewById(R.id.tietTexturedMatQty);
        String tietTexturedMatQtyStr = tietTexturedMatQty.getText().toString();
        int texturedMatQty;
        try {
            texturedMatQty = Integer.parseInt(tietTexturedMatQtyStr);
        } catch (NumberFormatException ex6) {
            Toast.makeText(this, "Textured Mat Qty input field must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize mat rate and isMatOversized variable
        boolean isMatOversized = (frameWidth >= 41 || frameHeight >= 41);
        int standardMatRate;
        int texturedMatRate;

        // Set mat rate based on size
        if (isMatOversized) {
            standardMatRate = oversizedStandardMatPrice;
            texturedMatRate = oversizedTexturedMatPrice;
        } else {
            standardMatRate = standardMatPrice;
            texturedMatRate = texturedMatPrice;
        }

        double dryMountRate = 0;
        double spacersRate = 30;
        double foamCoreRate;
        double addOnSubtotal = 0;

        // Check for Dry Mount and foam core Rate
        if (unitedInches <= 72) {
            foamCoreRate = 8;
            dryMountRate = 20;
        } else if ((unitedInches > 72) && (unitedInches < 144)){
            foamCoreRate = 15;
            dryMountRate = 40;
        }

        // Gather User Input from Chip Group
        Chip chipDryMount = findViewById(R.id.chipDrymount);
        if (chipDryMount.isChecked()) {
            addOnSubtotal = addOnSubtotal + dryMountRate;
        }
        Chip chipSpacers = findViewById(R.id.chipSpacers);
        if (chipSpacers.isChecked()) {
            addOnSubtotal = addOnSubtotal + spacersRate;
        }

        // Calculate Total Mat Prices
        int standardMatTotal = standardMatRate * standardMatQty;
        int texturedMatTotal = texturedMatRate * texturedMatQty;
        double totalMatPrice = standardMatTotal + texturedMatTotal;

        // Calculate Glass Price
        double totalCCGlassPrice;
        double totalMuseumGlassPrice;
        double ccGlassRate = 2.2;
        double museumGlassRate = 4.1;
        totalCCGlassPrice = (Math.ceil((frameWidth + frameHeight) * ccGlassRate));
        totalMuseumGlassPrice = (Math.ceil((frameWidth + frameHeight) * museumGlassRate));


        // Build a Summary and display
        TextView tvFrameSizeSummary = findViewById(R.id.tvTotalFrameSizeOutput);
        tvFrameSizeSummary.setText(getText(frameWidth, frameHeight));

        TextView materialSubtotalSummary = findViewById(R.id.tvFrameSubtotalOutput);
        materialSubtotalSummary.setText("$" + df.format(totalFramePrice));

        TextView matSubtotalSummary = findViewById(R.id.tvMatSubtotalOutput);
        matSubtotalSummary.setText("$" + df.format(totalMatPrice));

        TextView ccGlassSubtotalSummary = findViewById(R.id.tvCCGlassSubtotalOutput);
        ccGlassSubtotalSummary.setText("CC: $" + df.format(totalCCGlassPrice));

        TextView museumGlassSubtotalSummary = findViewById(R.id.tvMuseumGlassSubtotalOutput);
        museumGlassSubtotalSummary.setText("Museum: $" + df.format(totalMuseumGlassPrice));

        TextView tvAddOnSummary = findViewById(R.id.tvAddOnSubtotalOutput);
        tvAddOnSummary.setText("$" + df.format(addOnSubtotal));

    }

    @NonNull
    private static String getText(double frameWidth, double frameHeight) {
        return "W= " + frameWidth + " x " + "H= " + frameHeight;
    }


}