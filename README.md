# Audio Visualizer

## By: Keith Kutsuma & Kelsey Valencia

_Created for CU Boulder CSCI 4448 SU21 Final Project_

### Status Summary

#### Work Done

This past sprint has been spent prototyping and learning about JavaFX's API and creating the UI and visualizer for the application. Currently we have the visualizer created, pause and resume functions, the main scene UI, volume bar, and music time bar.

First, here are some of the things we learned & prototyped before starting the main project.

1. JavaFX .fxml files work 1-1 (.fxml - java controller), and will load/instantiate when called upon.
2. JavaFX MediaPlayer has an observer pattern for active/current media data that is being played. This means that if there's no sound there will be no information being feed to the listener.
  * What this meant for us is that if the music is paused and you reload the visualizer page there will be no information to update the scene.
  * We escalated this and it may be possible to cache this information upon page swapping but will have to be done at another time.
3. This is more of a development tip, but creating UI solely by fxml is not always the best way to do it. We had to change from using fxml to normal code to generate the visualizer's bars because we needed a way to reference each bar, and manually creating it in fxml and instantiating an array manually isn't a good idea.
4. At the start of the sprint we prototyped the MediaPlayer and how to load, play, and pause mp3 information.
5. Then we prototyped the scene changer. Doing some research [this](https://gist.github.com/jewelsea/6460130) has been considered as the best practice to handle fxml files. It is an upgrade to one of Oracle's examples of best practices. In addition we added a loader class so that we only have to change that class if we want to add more fxml files.

#### Changes & Issues

Because we have continued to prototype we had to make some changes to the design already. We still have the scene changer to control changing the main scenes. But within the visualizer scene we've broken it up into smaller fxml files. There are now three files, one controls the main part of the scene, one for the volume/timer bars, and one for the scene's buttons, (pause, resume, next page).

Because each of the pages have a separate controller, but all need to use the same MediaPlayer, we created a singleton class that will hold our MediaPlayer. It will hold one instance and each controller can get the media when the page loads.

This allows us to **not** store all of the actions of a scene into **one** controller. Instead we have multiple controllers and can change each module if we like. Because of this design, it should allow us to load different types of visualizer's to have different types of displays if we want.



### Project Set Up

To work on this project we are using JavaFX 16. You can find the latest JavaFX download [here](https://gluonhq.com/products/javafx/). To set up JavaFX you can find instructions [here](https://openjfx.io/openjfx-docs/).
