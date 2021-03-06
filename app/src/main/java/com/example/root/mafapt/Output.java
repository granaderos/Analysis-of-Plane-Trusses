package com.example.root.mafapt;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Output extends AppCompatActivity {

    private LinearLayout llContainer;
    private TableLayout tlJointDisplacement;
    private TableLayout tlMemberAxialForces;
    private TableLayout tlSupportReactions;

    public static ArrayList<Double[]> jointCoordinates;
    public static ArrayList<Integer[]> supports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        Intent intent = getIntent();
        String[] out = intent.getStringExtra("out").split("\n");
        String[] jointDisplacements = out[0].split(" ");
        String[] axialForces = out[1].split(" ");
        String[] reactions = out[2].split(" ");

        String[] sizes = intent.getStringExtra("sizes").split(" ");
        String[] data = intent.getStringExtra("data").split("\n");

        //NumberFormat formatter = new DecimalFormat("0.####E00");
        String format = "%.4E";

        jointCoordinates = new ArrayList<>();
        supports = new ArrayList<>();

        Double[] tempDoubleArray;
        Integer[] tempIntArray;
        String[] tempStringArray0, tempStringArray1;

        // Joint Coordinates

        if(Integer.parseInt(sizes[0]) > 1) {
            tempStringArray0 = data[0].split(":"); // Joint Coordinates
            for(int i = 0; i < tempStringArray0.length; i++) {
                tempStringArray1 = tempStringArray0[i].split(" ");
                tempDoubleArray = new Double[2];
                tempDoubleArray[0] = Double.parseDouble(tempStringArray1[0]);
                tempDoubleArray[1] = Double.parseDouble(tempStringArray1[1]);

                jointCoordinates.add(tempDoubleArray);
            }
        } else if(Integer.parseInt(sizes[0]) == 1) {
            tempStringArray0 = data[0].split(" ");
            tempDoubleArray = new Double[2];
            tempDoubleArray[0] = Double.parseDouble(tempStringArray0[0]);
            tempDoubleArray[1] = Double.parseDouble(tempStringArray0[1]);

            jointCoordinates.add(tempDoubleArray);
        }

        // Supports

        if(Integer.parseInt(sizes[2]) > 1) {
            tempStringArray0 = data[2].split(":"); // Supports
            for(int i = 0; i < tempStringArray0.length; i++) {
                tempStringArray1 = tempStringArray0[i].split(" ");
                tempIntArray = new Integer[3];
                tempIntArray[0] = Integer.parseInt(tempStringArray1[0]);
                tempIntArray[1] = Integer.parseInt(tempStringArray1[1]);
                tempIntArray[2] = Integer.parseInt(tempStringArray1[2]);

                supports.add(tempIntArray);
            }
        } else if(Integer.parseInt(sizes[2]) == 1){
            tempStringArray0 = data[2].split(" ");
            tempIntArray = new Integer[3];
            tempIntArray[0] = Integer.parseInt(tempStringArray0[0]);
            tempIntArray[1] = Integer.parseInt(tempStringArray0[1]);
            tempIntArray[2] = Integer.parseInt(tempStringArray0[2]);

            supports.add(tempIntArray);
        }

        llContainer = (LinearLayout) findViewById(R.id.llContainer);

        tlJointDisplacement = new TableLayout(this);
        tlMemberAxialForces = new TableLayout(this);
        tlSupportReactions = new TableLayout(this);

        TextView tempTV0, tempTV1, tempTV2;

        TableRow.LayoutParams tlCellParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        tlCellParams.setMargins(3, 3, 3, 3);

        LinearLayout.LayoutParams llTitleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        TableLayout.LayoutParams tlRowParams = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        // adding joint displacements to table layout

        tempTV0 = new TextView(this);
        tempTV0.setText("JOINT DISPLACEMENTS");
        tempTV0.setLayoutParams(llTitleParams);
        tempTV0.setTypeface(null, Typeface.BOLD);
        tempTV0.setGravity(Gravity.CENTER);
        tempTV0.setPadding(0, 15, 0, 10);
        llContainer.addView(tempTV0);

        TableRow row;

        row = new TableRow(this);
        row.setLayoutParams(tlRowParams);

        tempTV0 = new TextView(this);
        tempTV1 = new TextView(this);
        tempTV2 = new TextView(this);

        tempTV0.setText("Joint No.");
        tempTV1.setText("X Translation");
        tempTV2.setText("Y Translation");

        tempTV0.setTypeface(null, Typeface.BOLD);
        tempTV1.setTypeface(null, Typeface.BOLD);
        tempTV2.setTypeface(null, Typeface.BOLD);

        tempTV0.setLayoutParams(tlCellParams);
        tempTV1.setLayoutParams(tlCellParams);
        tempTV2.setLayoutParams(tlCellParams);

        tempTV0.setPadding(4, 4, 4, 4);
        tempTV1.setPadding(4, 4, 4, 4);
        tempTV2.setPadding(4, 4, 4, 4);


        row.addView(tempTV0);
        row.addView(tempTV1);
        row.addView(tempTV2);

        tlJointDisplacement.addView(row);

        ArrayList<Integer> withSupports = new ArrayList<>();
        for(int i = 0; i < supports.size(); i++) {
            withSupports.add(supports.get(i)[0]);
        }

        try {
            int jdIndex = 0;
            for(int i = 0; i < jointCoordinates.size(); i++) {
                row = new TableRow(this);
                row.setLayoutParams(tlRowParams);

                tempTV0 = new TextView(this);
                tempTV1 = new TextView(this);
                tempTV2 = new TextView(this);

                tempTV0.setText(String.valueOf(i+1));

                if(withSupports.contains(i+1)) {
                    int index = withSupports.indexOf(i+1);
                    if(supports.get(index)[1] == 1) {
                        //tempTV1.setText(String.valueOf(formatter.format(0.000000)));
                        tempTV1.setText(String.valueOf(String.format(format, 0.000000)));
                    } else {
                        //tempTV1.setText(String.valueOf(formatter.format(Double.parseDouble(jointDisplacements[jdIndex]))));
                        tempTV1.setText(String.valueOf(String.format(format, Double.parseDouble(jointDisplacements[jdIndex]))));
                        jdIndex = jdIndex + 1;
                    }
                    if(supports.get(index)[2] == 1) {
                        //tempTV2.setText(String.valueOf(formatter.format(0.000000)));
                        tempTV2.setText(String.valueOf(String.format(format, 0.000000)));
                    } else {
                        //tempTV2.setText(String.valueOf(formatter.format(Double.parseDouble(jointDisplacements[jdIndex]))));
                        tempTV2.setText(String.valueOf(String.format(format, Double.parseDouble(jointDisplacements[jdIndex]))));
                        jdIndex = jdIndex + 1;
                    }
                } else {
                    //tempTV1.setText(String.valueOf(formatter.format(Double.parseDouble(jointDisplacements[jdIndex]))));
                    tempTV1.setText(String.valueOf(String.format(format, Double.parseDouble(jointDisplacements[jdIndex]))));
                    jdIndex = jdIndex + 1;
//                    tempTV2.setText(String.valueOf(formatter.format(Double.parseDouble(jointDisplacements[jdIndex]))));
                    tempTV2.setText(String.valueOf(String.format(format, Double.parseDouble(jointDisplacements[jdIndex]))));
                    jdIndex = jdIndex + 1;
                }

                tempTV0.setLayoutParams(tlCellParams);
                tempTV1.setLayoutParams(tlCellParams);
                tempTV2.setLayoutParams(tlCellParams);

                tempTV0.setPadding(4, 4, 4, 4);
                tempTV1.setPadding(4, 4, 4, 4);
                tempTV2.setPadding(4, 4, 4, 4);

                row.addView(tempTV0);
                row.addView(tempTV1);
                row.addView(tempTV2);

                tlJointDisplacement.addView(row);
            }
        } catch(IndexOutOfBoundsException e) {
            //Toast.makeText(getApplicationContext(), "in displaying JD outOB = " + e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Ouchyyy; please recheck the data that you provided. Make sure they are valid. ~Mj"/* + e.getMessage() + " == " + e.getLocalizedMessage()*/, Toast.LENGTH_LONG).show();
            return;
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Ouchyyy; please recheck the data that you provided. Make sure they are valid. ~Mj"/* + e.getMessage() + " == " + e.getLocalizedMessage()*/, Toast.LENGTH_LONG).show();
            return;
            //Toast.makeText(getApplicationContext(), "Exception JD = " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        tlJointDisplacement.setGravity(Gravity.CENTER);
        llContainer.addView(tlJointDisplacement);

        // adding Member Axial Forces to table layout

        tempTV0 = new TextView(this);
        tempTV0.setText("MEMBER AXIAL FORCES");
        tempTV0.setLayoutParams(llTitleParams);
        tempTV0.setTypeface(null, Typeface.BOLD);
        tempTV0.setGravity(Gravity.CENTER);
        tempTV0.setPadding(0, 15, 0, 10);
        llContainer.addView(tempTV0);

        row = new TableRow(this);
        row.setLayoutParams(tlRowParams);

        tempTV0 = new TextView(this);
        tempTV1 = new TextView(this);
        tempTV2 = new TextView(this);

        tempTV0.setText("Member No.");
        tempTV1.setText("Axial Force");
        tempTV2.setText("(Qa)");

        tempTV0.setTypeface(null, Typeface.BOLD);
        tempTV1.setTypeface(null, Typeface.BOLD);
        tempTV2.setTypeface(null, Typeface.BOLD);

        tempTV0.setLayoutParams(tlCellParams);
        tempTV1.setLayoutParams(tlCellParams);
        tempTV2.setLayoutParams(tlCellParams);

        tempTV0.setPadding(4, 4, 4, 4);
        tempTV1.setPadding(4, 4, 4, 4);
        tempTV2.setPadding(4, 4, 4, 4);

        row.addView(tempTV0);
        row.addView(tempTV1);
        row.addView(tempTV2);

        tlMemberAxialForces.addView(row);

        for(int i = 0 ;i < axialForces.length; i++) {
            row = new TableRow(this);
            row.setLayoutParams(tlRowParams);

            tempTV0 = new TextView(this);
            tempTV1 = new TextView(this);
            tempTV2 = new TextView(this);

            tempTV0.setText(String.valueOf(i+1));
//            tempTV1.setText(String.valueOf(formatter.format(Math.abs(Double.parseDouble(axialForces[i])))));
            tempTV1.setText(String.valueOf(String.format(format, Math.abs(Double.parseDouble(axialForces[i])))));

            if(Double.parseDouble(axialForces[i]) < 0) {
                tempTV2.setText("T");
            } else {
                tempTV2.setText("C");
            }

            tempTV0.setLayoutParams(tlCellParams);
            tempTV1.setLayoutParams(tlCellParams);
            tempTV2.setLayoutParams(tlCellParams);

            tempTV0.setPadding(4, 4, 4, 4);
            tempTV1.setPadding(4, 4, 4, 4);
            tempTV2.setPadding(4, 4, 4, 4);

            row.addView(tempTV0);
            row.addView(tempTV1);
            row.addView(tempTV2);

            tlMemberAxialForces.addView(row);
        }
        tlMemberAxialForces.setGravity(Gravity.CENTER);
        llContainer.addView(tlMemberAxialForces);



        // adding Reactions to table layout

        tempTV0 = new TextView(this);
        tempTV0.setText("SUPPORT REACTIONS");
        tempTV0.setLayoutParams(llTitleParams);
        tempTV0.setTypeface(null, Typeface.BOLD);
        tempTV0.setGravity(Gravity.CENTER);
        tempTV0.setPadding(0, 15, 0, 10);
        llContainer.addView(tempTV0);

        row = new TableRow(this);
        row.setLayoutParams(tlRowParams);

        tempTV0 = new TextView(this);
        tempTV1 = new TextView(this);
        tempTV2 = new TextView(this);

        tempTV0.setText("Joint No.");
        tempTV1.setText("X FORCE");
        tempTV2.setText("Y FORCE");

        tempTV0.setTypeface(null, Typeface.BOLD);
        tempTV1.setTypeface(null, Typeface.BOLD);
        tempTV2.setTypeface(null, Typeface.BOLD);

        tempTV0.setLayoutParams(tlCellParams);
        tempTV1.setLayoutParams(tlCellParams);
        tempTV2.setLayoutParams(tlCellParams);

        tempTV0.setPadding(4, 4, 4, 4);
        tempTV1.setPadding(4, 4, 4, 4);
        tempTV2.setPadding(4, 4, 4, 4);


        row.addView(tempTV0);
        row.addView(tempTV1);
        row.addView(tempTV2);

        tlSupportReactions.addView(row);

        try {
            int reactionIndex = 0;
            for(int i = 0; i < supports.size(); i++) {
                row = new TableRow(this);
                row.setLayoutParams(tlRowParams);

                tempTV0 = new TextView(this);
                tempTV1 = new TextView(this);
                tempTV2 = new TextView(this);

                tempTV0.setText(String.valueOf(supports.get(i)[0]));

                if(supports.get(i)[1] != 0) {
//                    tempTV1.setText(String.valueOf(formatter.format(Double.parseDouble(reactions[reactionIndex]))));
                    tempTV1.setText(String.valueOf(String.format(format, Double.parseDouble(reactions[reactionIndex]))));
                    reactionIndex = reactionIndex + 1;
                } else {
//                    tempTV1.setText(String.valueOf(formatter.format(0.000000)));
                    tempTV1.setText(String.valueOf(String.format(format, 0.000000)));
                }
                if(supports.get(i)[2] != 0) {
//                    tempTV2.setText(String.valueOf(formatter.format(Double.parseDouble(reactions[reactionIndex]))));
                    tempTV2.setText(String.valueOf(String.format(format, Double.parseDouble(reactions[reactionIndex]))));
                    reactionIndex = reactionIndex + 1;
                } else {
                    //tempTV2.setText(String.valueOf(formatter.format(0.000000)));
                    tempTV2.setText(String.valueOf(String.format(format, 0.000000)));
                }

                tempTV0.setLayoutParams(tlCellParams);
                tempTV1.setLayoutParams(tlCellParams);
                tempTV2.setLayoutParams(tlCellParams);

                tempTV0.setPadding(4, 4, 4, 4);
                tempTV1.setPadding(4, 4, 4, 4);
                tempTV2.setPadding(4, 4, 4, 4);

                row.addView(tempTV0);
                row.addView(tempTV1);
                row.addView(tempTV2);

                tlSupportReactions.addView(row);
            }
            tlSupportReactions.setGravity(Gravity.CENTER);
            llContainer.addView(tlSupportReactions);

            Button btnNewData = new Button(this);
            btnNewData.setText("New Data");
            btnNewData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newDataIntent = new Intent(getApplicationContext(), Launcher.class);
                    startActivity(newDataIntent);
                }
            });
            llContainer.addView(btnNewData);
        } catch (IndexOutOfBoundsException e) {
            //Toast.makeText(getApplicationContext(), "reactions outOB = " + e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Ouchyyy; please recheck the data that you provided. Make sure they are valid. ~Mj"/* + e.getMessage() + " == " + e.getLocalizedMessage()*/, Toast.LENGTH_LONG).show();
            return;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ouchyyy; please recheck the data that you provided. Make sure they are valid. ~Mj"/* + e.getMessage() + " == " + e.getLocalizedMessage()*/, Toast.LENGTH_LONG).show();
            return;
            //Toast.makeText(getApplicationContext(), "reactions outOB = " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
