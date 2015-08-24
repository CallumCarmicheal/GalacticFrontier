# GalacticFrontier
A work-in-progress SolarSystem simulation based in JAVA.
+ MineCraft main menu OGL hook.

#Test Build
To get a test build goto the "Build" folder and select a version.
(Should work for Windows, Linux, Mac)

#Compiling from source
1. Download Git REPO
2. Open Eclipse
3. Select *YOUR* default workspace
4. Right click on Project -> Import -> Import 
5. click Existing Projects into Workspace 
6. Enter the git Repo location 
7. Select SolarSysm (great name huh?) 
8. Wait for it to start, then Right Click the project  -> Properties 
9. Build Path -> remove everything
10. Add External Jar GIT\lib\LWJGL\jar ADD EVERYTHING 
11. then also add Git\lib\slick-util\lib ADD EVERYTHING 
12. Goto Debug Configuration/Launch Config -> Create New Java App
13. Main Class, Search -> Select Init | Default Package
14. Arguments -> VM Args, 
    type -Djava.library.path="{REPLACE ME WITH GITHUB REPO LOC}\lib\lwjgl-2.9.3\native\windows"
	change windows if you are not on windows OS
15. Try to debug Debug, if it does not work create a issue report

Side Note: you may want to rename the debug config (Just a tip)


#RoadMap

REALISTIC

Progress      | Things To do                                                                 | Type
------------- | -------------                                                                | -------------
Done          | Basic Planets                                                                | Rendering
Done          | Basic Time/Day                                                               | Simulation
Done          | Basic Rotation                                                               | Rendering
Done          | Basic Text Render                                                            | Rendering
Done          | Planet API                                                                   | Simulation
Done          | SubPlanet API                                                                | Simulation
NO            | Implement Textures                                                           | Rendering
WIP           | Implement more planets in Sol                                                | Rendering / Simulation
NO            | Make a projection line around Sol                                            | Rendering / Simulation
NO            | Make a asteroid belt.                                                        | Simulation
NO            | Atmospheric glow from planets                                                | Rendering
NO            | Get a solar physicist to help out w/ equations                               | Simulation
NO            | Add mass to other planets to effect gravitational pull around and to the sun | Simulation
NO            | TURN PROJECTION LINE INTO MINATURE HAM-BURGERS (REQUEST FROM rockerBOO [LC]) | Rendering
NO            | A MAGICAL UNICORN EATS THE GALAXY (FINAL STEP: THE MOST IMPORTANT ONE!)      | REAL LIFE

WHISHLIST

Progress      | Thing To do        | Type
------------- | -------------      | -------------
NO            | Minecraft 3d World | Rendering
NO            | Minecraft Galaxy   | Rendering
NO            | Galaxy Exploration | Rendering/Sim
DONE          | 3d Navigation      | Rendering
NO            | Ram optimization   | EVERYTHING!


# Controls

Key    | Action
-------|-------
R      | Toggle Animation (ON/OFF)
G      | Toggle between original and generated code
U      | Toggle Projection Lines around the planets rotation
T      | Sets to single step (turns off Animation and increment hours by 1)
PGUP   | Increment Hour per Frame Skip on Animation
PGDOWN | Decrement Hour per Frame Skip on Animation
M      | Toggle grab mouse
E      | Move the camera down
Q      | Move the camera up
W      | Move camera Forward
LSHIFT | Speed up camera movement
LCTRL  | Slow down camera movement
S      | Move camera backwards
A      | Move camera left
D      | Move camera right
ArUP   | Rotate camera upwards
ArDOWN | Rotate camera downwards
ArLEFT | Rotate camera left
ArRIGHT| Rotate camera right (Yeah who would of guessed?)
MOUSE  | Rotate camera on XY
O      | Rotate the scene left
P      | Rotate the scene right
ESCAPE | Close Application