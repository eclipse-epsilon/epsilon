Acknowledgments:
 - The metamodel file (metamodel/simulink.ecore) was taken the Massif project (from https://github.com/viatra/massif) 
 - The models found under the model/emf/shallow directory were generated with the Massif project import facilities using the shallow approach. They are compressed in the "compressed-shallow.tar.bz2" file. To uncompress you can use the command `tar -jxvf compressed-shallow.tar.bz2` 
 - The models found under the model/live/ directory were taken from open projects in github. 
 - The native Simulink models were created for MATLAB 2018b version. To run the validation scripts it might be needed to have installed the MATLAB version 2018b.

Instructions:
 - Before you run the run configurations make sure you have installed the latest *interim* version of the  Epsilon Project and that you have provided in the Epsilon/Simulink eclipse preferences the path of the MATLAB Engine Jar and MATLAB Library.
 - To run the native validation script (management/validation/liveValidation.evl) use the example run configuration provided in run/liveValidation.launch
 - To run the EMF validation script (management/validation/emfValidation.evl) use the example run configuration provided in run/emfValidation.launch
 
Both example eclipse run configurations have been provided to run with the Simulink model "darkd0" found under the aerospace library of MATLAB/Simulink or in the exported EMF version in the directory model/emf/ of this project. The model can be changed by modifying the model file of the model dialog in both run configurations.  