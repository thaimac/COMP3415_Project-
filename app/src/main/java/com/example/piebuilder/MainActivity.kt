package com.example.piebuilder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.w3c.dom.Text

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    //XML variable set
    private lateinit var questionTextView: TextView
    private lateinit var input: EditText
    private lateinit var multiple_choice: ListView
    private lateinit var submitInput: Button
    private lateinit var submitInputMultChoice: Button
    private lateinit var questionViewMultChoice: TextView
    private lateinit var show_results: Button
    private lateinit var recommendation: TextView
    private lateinit var specific_holdings: TextView

    //Function variable set
    private var i = 0
    private var age = 0
    private var height = 0
    private var weight = 0
    private var targetWeight = 0
    private var length = ""
    private var frequency = ""
    private var fitnessLevelNow = ""
    private var buildGoal = ""
    private var workoutArea = ""
    private var weights = ""
    private var gym = ""

    private var weightDiff = 0  // for finding the difference between current and target weight


    private val appViewModel: AppViewModel by lazy{
        ViewModelProviders.of(this).get(AppViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        appViewModel.currIndex = currIndex

        //link XML components to their corresponding variables
        questionTextView = findViewById(R.id.question_text_view)
        input = findViewById(R.id.user_input)
        submitInput = findViewById(R.id.submit_input)

        //on click listener for text input questions
        submitInput.setOnClickListener { view: View ->
            Log.i("input submitted", "input submitted $i")
            when (i) {
                0 -> age = Integer.parseInt(input.getText().toString()) //save user's age
                1 -> height = Integer.parseInt(input.getText().toString()) // save user's height (need to make float)
                2 -> weight = Integer.parseInt(input.getText().toString()) // save user's weight (need to make float)
                3 -> {
                    targetWeight = Integer.parseInt(input.getText().toString()) // save user's target weight (need to make float)
                    multipleChoice() //function call to present multiple choice questions
                }
            }
            i++
            input.setText("")
            appViewModel.moveToNext()
            updateQuestion()

        }
    }

    //Function to display multiple choice questions and save user input
    private fun multipleChoice() {
        setContentView(R.layout.multiple_choice)

        submitInputMultChoice = findViewById(R.id.submit_input2)
        questionViewMultChoice = findViewById(R.id.question_text_view2)

        //two dimensional array to hold options for multiple choice questions
        val multChoice: Array<Array<String>> = arrayOf(
            arrayOf(  // for workout length
                "30 minutes or less",
                "1 hour",
                "1.5 - 2 hours",
                "3 hours",
                "More than 3 hours"
            ),
            arrayOf(  // for workout frequency
                "1 day per week",
                "2 days per week",
                "3 days per week",
                "4 days per week",
                "5 days per week",
                "6 days per week",
                "Every day"
            ),
            arrayOf(  // for fitness level now
                "Do not work out much at all",
                "Okay (work out once a month or so)",
                "Average (work out once every few weeks)",
                "Fairly fit (work out once every week, maybe with routines)",
                "Very fit (work out often, have routines, etc.)"
            ),
            arrayOf(  // for build goal
                "Endurance",
                "Strength",
                "Stamina",
                "Etc",
                "Etc"
            ),
            arrayOf(  // for workout area
                "Core",
                "Cardio",
                "Flexibility",
                "Lower Body",
                "Upper Body",
                "Full Body"
            ),
            arrayOf(  // for workout with or without weights
                "With Weights",
                "Without Weights"
            ),
            arrayOf(  // for in a gym vs at home
                "In a gym (with machines)",
                "At home (without machines)"
            )
        )

        multiple_choice = findViewById(R.id.user_input_multiple_choice)
        multiple_choice.choiceMode = ListView.CHOICE_MODE_SINGLE

        //use an adapter to display the multiple choice options. Iterate over the 2D array to display
        //possible answers to each question
        var adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, multChoice[0])
        multiple_choice.adapter = adapter
        multiple_choice.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                length = parent.getItemAtPosition(position).toString()
                appViewModel.moveToNext()
                updateQuestionMultChoice()
                adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, multChoice[1])
                multiple_choice.adapter = adapter
                multiple_choice.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        frequency = parent.getItemAtPosition(position).toString()
                        appViewModel.moveToNext()
                        updateQuestionMultChoice()
                        adapter = ArrayAdapter(
                            this,
                            android.R.layout.simple_list_item_activated_1,
                            multChoice[2]
                        )
                        multiple_choice.adapter = adapter
                        multiple_choice.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view, position, id ->
                                fitnessLevelNow = parent.getItemAtPosition(position).toString()
                                appViewModel.moveToNext()
                                updateQuestionMultChoice()
                                adapter = ArrayAdapter(
                                    this,
                                    android.R.layout.simple_list_item_activated_1,
                                    multChoice[3]
                                )
                                multiple_choice.adapter = adapter
                                multiple_choice.onItemClickListener =
                                    AdapterView.OnItemClickListener { parent, view, position, id ->
                                        buildGoal = parent.getItemAtPosition(position).toString()
                                        appViewModel.moveToNext()
                                        updateQuestionMultChoice()
                                        adapter = ArrayAdapter(
                                            this,
                                            android.R.layout.simple_list_item_activated_1,
                                            multChoice[4]
                                        )
                                        multiple_choice.adapter = adapter
                                        multiple_choice.onItemClickListener =
                                            AdapterView.OnItemClickListener { parent, view, position, id ->
                                                workoutArea = parent.getItemAtPosition(position).toString()
                                                appViewModel.moveToNext()
                                                updateQuestionMultChoice()
                                                adapter = ArrayAdapter(
                                                    this,
                                                    android.R.layout.simple_list_item_activated_1,
                                                    multChoice[5]
                                                )
                                                multiple_choice.adapter = adapter
                                                multiple_choice.onItemClickListener =
                                                    AdapterView.OnItemClickListener { parent, view, position, id ->
                                                        weights = parent.getItemAtPosition(position).toString()
                                                        appViewModel.moveToNext()
                                                        updateQuestionMultChoice()
                                                        adapter = ArrayAdapter(
                                                            this,
                                                            android.R.layout.simple_list_item_activated_1,
                                                            multChoice[6]
                                                        )
                                                        multiple_choice.adapter = adapter
                                                        multiple_choice.onItemClickListener =
                                                            AdapterView.OnItemClickListener { parent, view, position, id ->
                                                                gym = parent.getItemAtPosition(position).toString()
                                                                //create User instance with the user's answers
                                                                val user = User(
                                                                    age,
                                                                    height,
                                                                    weight,
                                                                    targetWeight,
                                                                    length,
                                                                    frequency,
                                                                    fitnessLevelNow,
                                                                    buildGoal,
                                                                    workoutArea,
                                                                    weights,
                                                                    gym
                                                                )

                                                                setContentView(R.layout.get_results)
                                                                show_results =
                                                                    findViewById(R.id.show_results)
                                                                show_results.setOnClickListener { view: View ->
                                                                    showResults(user)
                                                                }
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
            }
    }

    //function to update multiple choice question
    private fun updateQuestionMultChoice(){
        val questionTextResId = appViewModel.currentQuestionText
        questionViewMultChoice.setText(questionTextResId)
    }

    //function to update regular question
    private fun updateQuestion(){
        val questionTextResId = appViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    //function to recommend a portfolio to the user
    private fun showResults(user: User) {
        setContentView(R.layout.show_results)
        recommendation = findViewById(R.id.asset_alloc)
        specific_holdings = findViewById(R.id.specific_holdings)
        getInvestmentProfile(user)
    }

    private fun getInvestmentProfile(user: User) {
        var points = 0

        //points for age
        if(user.age > 0 && user.age <= 30) {
            points += 3
        } else if(user.age > 30 && user.age <= 40) {
            points += 2
        } else if(user.age > 40 && user.age <= 50) {
            points += 1
        } else if(user.age > 50) {
            points += 0
        }
/*
        //points for horizon
        if(user.horizon == "15+ years") {
            points += 4
        } else if(user.horizon == "10-14 years") {
            points += 3
        } else if(user.horizon == "5-9 years") {
            points += 2
        } else if(user.horizon == "1-4 years") {
            points += 1
        } else if(user.horizon == "Less than 1 year") {
            points -= 5
        }

        //points for objective
        if(user.objective == "Maximum asset growth") {
            points += 4
        } else if(user.objective == "Achieve better than average asset growth") {
            points += 3
        } else if(user.objective == "Achieve balanced growth with a moderate level of risk") {
            points += 2
        } else if(user.objective == "Have a steady stream of income") {
            points += 1
        } else if(user.objective == "Preserve capital") {
            points += 0
        }

        //points for volatility
        if(user.volatility == "Time to buy") {
            points += 4
        } else if(user.volatility == "I expect fluctuations") {
            points += 3
        } else if(user.volatility == "It's all good") {
            points += 2
        } else if(user.volatility == "It doesn't feel great, but I'm okay") {
            points += 1
        } else if(user.volatility == "That's it, I'm selling everything") {
            points -= 3
        }

        //points for ideal portfolio
        if(user.ideal_portfolio == "Maximize returns while accepting large account value fluctuation. Best case 17.2%. Average 10.05%. Worst case: -15.6%") {
            points += 5
        } else if(user.ideal_portfolio == "Seek greater returns while taking on more risk. Best case 12.1%. Average 7.47%. Worst case: -10.8%") {
            points += 3
        } else if(user.ideal_portfolio == "Seek medium returns while taking on some risk. Best case 7.8%. Average 6.19%. Worst case: -7%") {
            points += 2
        } else if(user.ideal_portfolio == "Keep risk low while seeking modest returns. Best case 5.6%. Average 3.86%. Worst case: -4.8%") {
            points += 1
        } else if(user.ideal_portfolio == "Avoid losses while accepting lower returns. Best case 3.7%. Average 2.26%. Worst case: -3%") {
            points -= 2
        }
*/
        //fit user into an investment profile based on the number of points
        if(points >= 13) {
            recommendation.setText(R.string.high_risk_profile)
            specific_holdings.setText(R.string.high_risk_holdings)
        } else if(points < 13 && points >= 5) {
            recommendation.setText(R.string.medium_risk_profile)
            specific_holdings.setText(R.string.medium_risk_holdings)
        } else if(points < 5) {
            recommendation.setText(R.string.low_risk_profile)
            specific_holdings.setText(R.string.low_risk_holdings)
        }
    }
}