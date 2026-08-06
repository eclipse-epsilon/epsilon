%function makeMyModel
rt = sfroot;
prev_models = rt.find('-isa','Simulink.BlockDiagram');

% Create new model, and get current models
sfnew; % This creates a new SL model with a SF chart element

%new_system('model1', 'Model'); % Simulink way
%load_system model1

curr_models = rt.find('-isa','Simulink.BlockDiagram');

% New model is current models - previous models
m = setdiff(curr_models, prev_models);

% Get chart in new model
ch = m.find('-isa', 'Stateflow.Chart');

% Create state A in chart
sA = Stateflow.State(ch);
sA.Name = 'A';
sA.Position = [50 50 310 200];

% Create state A1 inside of state A
sA1 = Stateflow.State(ch);
sA1.Name = 'A1';
sA1.Position = [80 120 90 60];

% Create state A2 inside of state A
sA2 = Stateflow.State(ch);
sA2.Name = 'A2';
sA2.Position = [240 120 90 60];

% Create a transition from A1 to A2
tA1A2 = Stateflow.Transition(ch);
tA1A2.Source = sA1;
tA1A2.Destination = sA2;
tA1A2.SourceOClock = 3;
tA1A2.DestinationOClock = 9;

% Label transition from state A1 to state A2
tA1A2.LabelPosition = [180 140 0 0];
tA1A2.LabelString = 'E1';

% Create the Event E1
E1 = Stateflow.Event(ch);
E1.Name = 'E1';

% Move label for transition A1-A2 to the right a bit
pos = tA1A2.LabelPosition;
pos(1) = pos(1)+5;
tA1A2.LabelPosition = pos;

% Add a default transition to state A
dtA = Stateflow.Transition(ch);
dtA.Destination = sA;
dtA.DestinationOClock = 0;
xsource = sA.Position(1)+sA.Position(3)/2;
ysource = sA.Position(2)-30;
dtA.SourceEndPoint = [xsource ysource];
dtA.MidPoint = [xsource ysource+15];

% Add a default transition to state A1
dtA1 = Stateflow.Transition(ch);
dtA1.Destination = sA1;
dtA1.DestinationOClock = 0;
xsource = sA1.Position(1)+sA1.Position(3)/2;
ysource = sA1.Position(2)-30;
dtA1.SourceEndPoint = [xsource ysource];
dtA1.MidPoint = [xsource ysource+15];

% CODE ABOVE IS SIMILAR TO EXAMPLE IN STATEFLOW API
% ===============================
% CODE BELOW IS USED FORT TESTING 
