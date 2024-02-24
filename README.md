Blots is an experimental project created to explore the capabilities of Jetpack Compose in rendering complex animations and shapes. This project focuses on generating dynamic, animated blots using Bezier curves and controlling their behavior through a user interface.

## Overview

<details>
<summary>Example(~4MB)</summary>
<br>
![GIF](https://github.com/viktordolgalyov/Blots/blob/main/animation_demo.gif)
</details>

The core of the Blots animation lies in its algorithmic approach to drawing and animating Bezier curves. Each blot is a composition of multiple cubic Bezier curves, calculated to form a closed, organic shape. Animations are applied to the properties of these curves, as well as the overall blot, creating a lively, ever-changing canvas of shapes.

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

## Key Features

- `Dynamic Blot Generation`: Utilizes Bezier curves to create unique, animated blot shapes.
- `Interactive Controls`: Offers sliders to adjust the number of blots, their size, curvature, and animation speed dynamically.
- `Responsive Layout`: Adapts to both portrait and landscape orientations, showcasing different layout strategies in Jetpack Compose.
- `Animation Exploration`: Demonstrates the use of Animatable and custom animation specs to animate properties like scale, opacity, and rotation.

## Contribution

Feel free to fork this project and experiment with different animation techniques or improve the existing algorithm. Contributions are welcome!

## License

This project is open-sourced under the MIT license. Feel free to use it as you please.