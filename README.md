# ASAP Courier Service
ASAP is a courier service system. Currently, offer criteria is using weight and distance criteria. This ASAP system provides extensibility to add our own offer criteria as well.
This provides extensibility to create new offers as well.

###Application functionalities:

- Calculate total cost of a package and discount if any offerCode applied
- Calculate estimated time of delivery of a package

###Available Offer codes:
Currently, there are only 3 offer codes available. If applied offer code is other than these 3 then discount will be 0.
- `OFR001`
- `OFR002`
- `OFR003`

Anyway, this application provides flexibility to add more offers as well.

## Quick Start

Getting started with the ASAP courier service is easy. It is configured to run out of the box with minimal setup.

### Step 1: Download Java (11 and above) for your platform

To use this application, you need a working installation of [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and [Gradle](https://gradle.org/).

The application was built using the Gradle plugin for IntelliJ IDEA IDE. The project build.gradle file is configured to let you run the application from within IntelliJ.

### Step 2: Install Gradle

If you do not have the Gradle build system installed, [install Gradle](https://docs.gradle.org/4.6/userguide/installation.html).

### Step 3: Enable annotation processor

This application uses lombok project. So in order to run this application, please make sure `annotation processor` is enabled in your IDE.

### Step 4: Download the ASAP Application source code

Next, clone the sample repository and install the project's dependencies.

From your shell or command line:

* `$ git clone https://github.com/lavanyak2020/ASAP.git`
* `$ cd ASAP`

### Step 5: Run the Application

Run [ASAPApplication](src/main/java/com/ee/asap/ASAPApplication.java) which contains main method. 

### Running the Application

You are prompted for Inputs. Please provide all the inputs. Once you are done with inputs, you can find output in console.

Sample input:

```Shell
Please enter Base delivery cost: 100

Enter No. of vehicles: 2

Enter Max Speed: 60

Enter Max Carriable weight: 200

No. of packages: 2

PackageID:P1
Weight(in kgs):50
Distance(in kms):10
offerCode:OFR001

PackageID:p2
Weight(in kgs):30
Distance(in kms):50
offerCode:OFR002
```

Sample Output:
```shell
PackageID: P1 Discount: 0.0 Rupees Total cost: 650.0 Rupees Estimated delivery time(in hours): 0.16666666666666666 Hours
PackageID: p2 Discount: 0.0 Rupees Total cost: 650.0 Rupees Estimated delivery time(in hours): 0.8333333333333334 Hours
```
