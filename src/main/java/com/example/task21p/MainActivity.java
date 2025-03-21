package com.example.task21p; // Adjust to match your package name

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Main menu buttons
    private Button btnLengthMenu, btnWeightMenu, btnTemperatureMenu;

    // Layout sections
    private LinearLayout layoutLength, layoutWeight, layoutTemperature;

    // LENGTH views
    private Spinner spinnerLengthFrom, spinnerLengthTo;
    private EditText editTextLengthValue;
    private Button btnConvertLength;
    private TextView textViewLengthResult;

    // WEIGHT views
    private Spinner spinnerWeightFrom, spinnerWeightTo;
    private EditText editTextWeightValue;
    private Button btnConvertWeight;
    private TextView textViewWeightResult;

    // TEMPERATURE views
    private Spinner spinnerTemperatureFrom, spinnerTemperatureTo;
    private EditText editTextTemperatureValue;
    private Button btnConvertTemperature;
    private TextView textViewTemperatureResult;

    // Arrays for each converter
    private final String[] lengthUnits = {"Inch", "Foot", "Yard", "Mile", "Centimeter", "Kilometer"};
    private final String[] weightUnits = {"Pound", "Ounce", "Ton", "Kilogram", "Gram"};
    private final String[] tempUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setupMenuButtons();
        setupLengthConverter();
        setupWeightConverter();
        setupTemperatureConverter();
    }

    // -----------------------
    // Bind All Views
    // -----------------------
    private void bindViews() {
        // Main Menu
        btnLengthMenu = findViewById(R.id.btnLengthMenu);
        btnWeightMenu = findViewById(R.id.btnWeightMenu);
        btnTemperatureMenu = findViewById(R.id.btnTemperatureMenu);

        // Layout Sections
        layoutLength = findViewById(R.id.layout_length);
        layoutWeight = findViewById(R.id.layout_weight);
        layoutTemperature = findViewById(R.id.layout_temperature);

        // Length
        spinnerLengthFrom = findViewById(R.id.spinnerLengthFrom);
        spinnerLengthTo = findViewById(R.id.spinnerLengthTo);
        editTextLengthValue = findViewById(R.id.editTextLengthValue);
        btnConvertLength = findViewById(R.id.btnConvertLength);
        textViewLengthResult = findViewById(R.id.textViewLengthResult);

        // Weight
        spinnerWeightFrom = findViewById(R.id.spinnerWeightFrom);
        spinnerWeightTo = findViewById(R.id.spinnerWeightTo);
        editTextWeightValue = findViewById(R.id.editTextWeightValue);
        btnConvertWeight = findViewById(R.id.btnConvertWeight);
        textViewWeightResult = findViewById(R.id.textViewWeightResult);

        // Temperature
        spinnerTemperatureFrom = findViewById(R.id.spinnerTemperatureFrom);
        spinnerTemperatureTo = findViewById(R.id.spinnerTemperatureTo);
        editTextTemperatureValue = findViewById(R.id.editTextTemperatureValue);
        btnConvertTemperature = findViewById(R.id.btnConvertTemperature);
        textViewTemperatureResult = findViewById(R.id.textViewTemperatureResult);
    }

    // -----------------------
    // Menu Button Clicks
    // -----------------------
    private void setupMenuButtons() {
        btnLengthMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLengthLayout();
            }
        });

        btnWeightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWeightLayout();
            }
        });

        btnTemperatureMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTemperatureLayout();
            }
        });
    }

    private void showLengthLayout() {
        layoutLength.setVisibility(View.VISIBLE);
        layoutWeight.setVisibility(View.GONE);
        layoutTemperature.setVisibility(View.GONE);
    }

    private void showWeightLayout() {
        layoutLength.setVisibility(View.GONE);
        layoutWeight.setVisibility(View.VISIBLE);
        layoutTemperature.setVisibility(View.GONE);
    }

    private void showTemperatureLayout() {
        layoutLength.setVisibility(View.GONE);
        layoutWeight.setVisibility(View.GONE);
        layoutTemperature.setVisibility(View.VISIBLE);
    }

    // -----------------------
    // Setup Length Converter
    // -----------------------
    private void setupLengthConverter() {
        // Populate spinners
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, lengthUnits);
        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLengthFrom.setAdapter(lengthAdapter);
        spinnerLengthTo.setAdapter(lengthAdapter);

        btnConvertLength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editTextLengthValue.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(MainActivity.this, "Please enter a length value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value;
                try {
                    value = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fromUnit = spinnerLengthFrom.getSelectedItem().toString();
                String toUnit = spinnerLengthTo.getSelectedItem().toString();

                if (fromUnit.equals(toUnit)) {
                    textViewLengthResult.setText("Result: " + value);
                    return;
                }

                double result = convertLength(value, fromUnit, toUnit);
                textViewLengthResult.setText("Result: " + result);
            }
        });
    }

    private double convertLength(double value, String fromUnit, String toUnit) {
        // Convert fromUnit -> centimeters
        double inCm = value * lengthFactorInCm(fromUnit);
        // centimeters -> toUnit
        return inCm / lengthFactorInCm(toUnit);
    }

    private double lengthFactorInCm(String unit) {
        // 1 inch = 2.54 cm
        // 1 foot = 30.48 cm
        // 1 yard = 91.44 cm
        // 1 mile = 160934 cm
        // 1 centimeter = 1
        // 1 kilometer = 100000 cm
        switch (unit) {
            case "Inch":
                return 2.54;
            case "Foot":
                return 30.48;
            case "Yard":
                return 91.44;
            case "Mile":
                return 160934;
            case "Centimeter":
                return 1;
            case "Kilometer":
                return 100000;
            default:
                return 1;
        }
    }

    // -----------------------
    // Setup Weight Converter
    // -----------------------
    private void setupWeightConverter() {
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, weightUnits);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerWeightFrom.setAdapter(weightAdapter);
        spinnerWeightTo.setAdapter(weightAdapter);

        btnConvertWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editTextWeightValue.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(MainActivity.this, "Please enter a weight value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value;
                try {
                    value = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fromUnit = spinnerWeightFrom.getSelectedItem().toString();
                String toUnit = spinnerWeightTo.getSelectedItem().toString();

                if (fromUnit.equals(toUnit)) {
                    textViewWeightResult.setText("Result: " + value);
                    return;
                }

                double result = convertWeight(value, fromUnit, toUnit);
                textViewWeightResult.setText("Result: " + result);
            }
        });
    }

    private double convertWeight(double value, String fromUnit, String toUnit) {
        // Convert fromUnit -> kg
        double inKg = value * weightFactorInKg(fromUnit);
        // kg -> toUnit
        return inKg / weightFactorInKg(toUnit);
    }

    private double weightFactorInKg(String unit) {
        // 1 pound = 0.453592 kg
        // 1 ounce = 0.0283495 kg
        // 1 ton = 907.185 kg
        // 1 kilogram = 1
        // 1 gram = 0.001
        switch (unit) {
            case "Pound":
                return 0.453592;
            case "Ounce":
                return 0.0283495;
            case "Ton":
                return 907.185;
            case "Kilogram":
                return 1;
            case "Gram":
                return 0.001;
            default:
                return 1;
        }
    }

    // -----------------------
    // Setup Temperature Converter
    // -----------------------
    private void setupTemperatureConverter() {
        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tempUnits);
        tempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTemperatureFrom.setAdapter(tempAdapter);
        spinnerTemperatureTo.setAdapter(tempAdapter);

        btnConvertTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editTextTemperatureValue.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(MainActivity.this, "Please enter a temperature value", Toast.LENGTH_SHORT).show();
                    return;
                }

                double value;
                try {
                    value = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fromUnit = spinnerTemperatureFrom.getSelectedItem().toString();
                String toUnit = spinnerTemperatureTo.getSelectedItem().toString();

                if (fromUnit.equals(toUnit)) {
                    textViewTemperatureResult.setText("Result: " + value);
                    return;
                }

                double result = convertTemperature(value, fromUnit, toUnit);
                textViewTemperatureResult.setText("Result: " + result);
            }
        });
    }

    private double convertTemperature(double value, String fromUnit, String toUnit) {
        // Celsius -> Fahrenheit: F = (C * 1.8) + 32
        // Fahrenheit -> Celsius: C = (F - 32) / 1.8
        // Celsius -> Kelvin: K = C + 273.15
        // Kelvin -> Celsius: C = K - 273.15
        double inCelsius = toCelsius(value, fromUnit);
        return fromCelsius(inCelsius, toUnit);
    }

    private double toCelsius(double value, String unit) {
        switch (unit) {
            case "Fahrenheit":
                return (value - 32) / 1.8;
            case "Kelvin":
                return value - 273.15;
            default: // Celsius
                return value;
        }
    }

    private double fromCelsius(double celsius, String unit) {
        switch (unit) {
            case "Fahrenheit":
                return (celsius * 1.8) + 32;
            case "Kelvin":
                return celsius + 273.15;
            default: // Celsius
                return celsius;
        }
    }
}
