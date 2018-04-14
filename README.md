## Android plugin kotlinx.android.synthetic cannot handle resources across multiple flavors

#### Issue

When using multiple flavors in an Android project you should be able to access product flavor views using the synthetic imports AND the project shluld be able to compile.

Using `import kotlinx.android.synthetic.internal.activity_internal.*` does not work when using more than one flavor. Using `import kotlinx.android.synthetic.main.activity_internal.*` still works if I move the resources.

For background I found this issue when adding another level of flavors. These imports work when there is only 1 level of product flavors.

#### Steps to Reproduce

1. Setup multiple flavor levels in an Android project like and turn the Android `experimental` flag:

        androidExtensions {
            experimental = true
        }

        flavorDimensions "build_type", "destination"
        
        productFlavors {
            development {
                dimension "build_type"
            }
            alpha {
                dimension "build_type"
                applicationIdSuffix '.alpha'
            }
            beta {
                dimension "build_type"
                applicationIdSuffix '.beta'
            }
            production {
                dimension "build_type"
            }
        
            internal {
                dimension "destination"
                applicationIdSuffix '.internal'
            }
        
            store {
                dimension "destination"
            }
        }

2. Create the necessary folder structure for the ![flavors](https://cl.ly/3w2l190e3j1U/Image%202018-04-14%20at%209.57.03%20AM.png)

3. Create code that is only used in a product lavor and try to import the synthetic views (notice how Android studio doesn't complain about the impport so everything seems fine):

        package com.leneau.syntheticexample
        
        import android.support.v7.app.AppCompatActivity
        import android.os.Bundle
        import android.widget.TextView
        import kotlinx.android.synthetic.internal.activity_internal.*
        
        class InternalActivity : AppCompatActivity() {
        
            internal lateinit var text: TextView
        
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_internal)
        
                text = internal_text
            }
        }

4. Compile the project with gradle OR launch from Android studio

        ./gradlew assembleAlphaInternalDebug

5. Notice that that build fails everytime:

        Parallel execution with configuration on demand is an incubating feature.
        e: /Users/kyle/Development/SyntheticExample/app/src/internal/java/com/leneau/syntheticexample/InternalActivity.kt: (6, 34): Unresolved reference: internal
        e: /Users/kyle/Development/SyntheticExample/app/src/internal/java/com/leneau/syntheticexample/InternalActivity.kt: (16, 16): Unresolved reference: internal_text
        
        FAILURE: Build failed with an exception.
        
        * What went wrong:
        Execution failed for task ':app:compileAlphaInternalDebugKotlin'.
        Compilation error. See log for more details

#### Expected Results

1. The project should be able to compile.
1. Multiple levels of product flavors should be supported.
1. Android studio should reflect the issue ahead of time.