# Blots Animation Demo

This project is a fun exploration into the world of animations with Jetpack Compose, focusing on the creation and animation of blots that dynamically change based on user input. It's a showcase of custom drawing, animation, and state management in Compose, aimed at creating visually appealing patterns and motions.

## Overview

The heart of this demo is the animation of blots—abstract shapes that move, scale, and transform. Each blot is drawn using Bezier curves, providing a smooth and organic feel. The animation is controlled through sliders, allowing real-time adjustments of various parameters such as the count, size, and dynamics of the blots.

## Algorithm and Drawing

The algorithm for drawing and animating the blots involves several key steps:

1. **Initialization**: Sets up the initial state of each blot, including its size, position, and animation parameters.
2. **Drawing**: Utilizes the Canvas API to draw each blot based on its current state. This involves calculating Bezier curves to create the organic shapes of the blots.
3. **Animation**: Applies animations to the properties of each blot (e.g., scale, opacity, rotation) using Compose's `Animatable` API. The animations are synchronized and can be adjusted in real-time via UI controls.
4. **User Interaction**: Sliders provide control over the animation parameters, allowing users to experiment with the appearance and behavior of the blots.

## Getting Started

To explore this project, simply clone the repository and open it in Android Studio:

```shell
git clone https://github.com/viktordolgalyov/blots.git
```

Build the project in Android Studio and run it on an emulator or physical device to see the animation in action.

## Exploring the Code

The code is structured around Compose's composable functions, with a strong emphasis on modular design and reusability. Key components include:

- `BlotView`: Responsible for drawing a single blot.
- `BlotsView`: Manages the collection of blots and orchestrates their animation.
- `BlotsControls`: Provides UI controls for adjusting animation parameters.

Dive into the code to see how Compose's powerful features are used to create complex animations with minimal boilerplate.

## Contribution

Feel free to fork this project and experiment with different animation techniques or improve the existing algorithm. Contributions are welcome!

## License

This project is open-sourced under the MIT license. Feel free to use it as you please.