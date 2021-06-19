# Contacts

Contacts app represents a real world app example: it provides a fairly complex set of functionalities,
it's a suitable showcase for all the advantages that architecture components bring, 
has all features that would make it a modular, scalable, testable and maintainable app.
 
## App Logic

- [] The app the list of user’s contacts that are saved in `Room` local database.
- [] If there's no data in `Room`, it will be retrieved from contacts `ContentPorvider` and saved locally in `Room`.
- [] The local contacts are synchronized with `ContentPorvider` in background every 15 minutes using `WorkManager`.
- [] The local contacts are synchronized every time the user open the app.

## Modular Architecture
 
<img src="https://github.com/ShabanKamell/Contacts/blob/master/blob/modular-arch-diagram3.png?raw=true" height="600">
  
The app is divided into 3 modules under `common` package. Each package has its own responsibilities.
  
#### Core

Core module contains all shared classes that are visible to all modules. 
Here we put all the code that we need to share with any module in the app.

#### Data

Data module contains any classes related to the data in the app. 
It contains network, local database, SharedPreferences, model classes, and any data-related classes.

#### Presentation

Presentation modules contains all UI classes
 including view models, base activities, base fragments, custom views, and other common views.

 ## Google Architecture Components
 
 The app implements Google architecture components. Visit the [Guide](https://developer.android.com/jetpack/docs/guide) for the details.
 
 <img src="https://github.com/ShabanKamell/Contacts/blob/master/blob/google-architecture-components.png?raw=true" height="500">

## Unit Tests

You can find tests in `app` and `data' modules.

## README

Each module has its own `README.md` file that documents the module responsibilities.
 
 ### Credit
- [ ] [Build a Modular Android App Architecture (Google I/O'19)](https://www.youtube.com/watch?v=PZBg5DIzNww)
- [ ] [Plaid](https://github.com/android/plaid)

### 🛡 License
<details>
    <summary>
        Click to reveal License
    </summary>
    
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
