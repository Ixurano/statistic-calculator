package fi.arcada.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextAge;
    TextView textViewCalcOut;
    RecyclerView recyclerViewPersons;
    PersonsAdapter personsAdapter;

    ArrayList<Person> persons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);

        textViewCalcOut = findViewById(R.id.textViewCalcOut);


        recyclerViewPersons = findViewById(R.id.recyclerViewPersons);

        personsAdapter = new PersonsAdapter(this, persons);
        recyclerViewPersons.setAdapter(personsAdapter);
        recyclerViewPersons.setLayoutManager(new LinearLayoutManager(this));


    }

    public void addPerson(View view) {

        if (TextUtils.isEmpty(editTextName.getText()) || TextUtils.isEmpty(editTextAge.getText())) {
            Toast message = Toast.makeText(this, "Fyll i båda fälten!", Toast.LENGTH_SHORT);
            message.show();
        } else {
            String personName = editTextName.getText().toString();
            int personAge = Integer.parseInt(
                    editTextAge.getText().toString());

            editTextName.setText("");
            editTextAge.setText("");
            // Typ variabel new klasskonstuktor onödigt ibland blir dubbel
            //Person person = new Person(personName, personAge);
            persons.add(new Person(personName, personAge));
            personsAdapter.notifyItemInserted(persons.size() - 1);
        }

    }

    @SuppressLint("DefaultLocale")
    public void calculate(View view) {

        ArrayList<Double> dataset = new ArrayList<>();

        for (Person person : persons) {
            dataset.add((double) person.getAge());
        }

        // Vi använder String.format för att skriva ut alla våra uträkningar i vår View
        textViewCalcOut.setText(String.format("%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f\n%s: %.2f",
                "Minstavärdet", Statistics.calcMin(dataset),
                "Störstavärdet", Statistics.calcMax(dataset),
                "Medelvärde", Statistics.calcAverage(dataset),
                "Medianvärde", Statistics.calcMedian(dataset),
                "Typvärde:", Statistics.calcMode(dataset),
                "Standardavvikelse", Statistics.calcSD(dataset),
                "Nedre kvartil:", Statistics.calcLQ(dataset),
                "Övre kvartil", Statistics.calcUQ(dataset),
                "Kvartilavstånd;", Statistics.calcQR(dataset)
        ));
    }

}