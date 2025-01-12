# PostmanAssertify

PostmanAssertify is a tool for the automated generation of test assertions for REST API testing. PostmanAssertify is part of the AGORA approach for the **A**utomated **G**eneration of test **O**racles for **R**EST **A**PIs through the detection of invariants (properties of the output that should always hold).

AGORA aims to learn the expected behavior of an API by analyzing previous API requests and their corresponding responses. AGORA operates in black-box mode, making it applicable to any REST API and seamlessly integrable into existing API testing tools.

PostmanAssertify takes as input the OpenAPI Specification (OAS) of the API under test and a CSV containing the set of confirmed invariants generated by AGORA. As a result, it returns a Postman collection that includes a sample API request for each status code of the API operations under test and (attached to each request) all the invariants as executable assertions.

If you want to use AGORA or know more details about the approach, please refer to the official [*AGORA repository*](https://github.com/isa-group/Beet) and the [*AGORA paper*](https://dl.acm.org/doi/10.1145/3597926.3598114)  (pre-print available [here](https://www.javalenzuela.com/publication/2023_issta_agora/2023_issta_agora.pdf)).



# Index

1. [Executing PostmanAssertify](#executing-postmanassertify)
2. [Publication](#publication)
3. [Input CSV format](#input-csv-format)

# Executing PostmanAssertify

This section describes how to use PostmanAssertify to generate a Postman collection with the generated assertions from an OAS specification and a CSV file containing the set of confirmed invariants. To execute PostmanAssertify, run the `GeneratePostmanCollection` class. This class receives the following parameters:
- `openApiSpecPath`: Relative path of the OpenAPI Specification of the API under test. This OAS specification must be the same as the one used by AGORA to detect the invariants.
- `invariantsPath`: Relative path of the CSV file containing the invariants detected by AGORA. The user must confirm the invariants as valid by adding the `tp` column (see the [Input CSV format](#input-csv-format) section for more details).
- `valuesToConsiderAsNull`: Some APIs use empty strings (“”) or specific keywords instead of null values. For instance, the OMDb API uses the string “N/A” instead of setting a response field as null. You can use this parameter to specify a set of keywords that can be considered as null values, and thus ignored.
- `DEBUG_MODE`: Set this Boolean parameter to true if you want PostmanAssertify to print the values of every variable involved in the test cases. This can make the execution considerably slower.
- `FORMAT_JS_CODE`: Set this Boolean parameter to true to apply indentation to the generated test scripts.

![Executing PostmanAssertify](https://i.imgur.com/RSSHyel.png)

PostmanAssertify requires JDK 17 to run. The `src/main/resources/example` directory of the repository contains files for executing an example of assertion generation for the Spotify API.

![Postman collection generated by PostmanAssertify](https://i.imgur.com/iFSYWbG.png)

The screenshot above shows an example of a Postman collection generated by PostmanAssertify running on the Postman desktop application. The collection contains a request for each response code of all the API operations for which AGORA has detected invariants (directories in the left sidebar). Each request (such as the one in the right menu) contains the URI of the API operation, with the input parameters configured, and a test script written in JavaScript using the [Chai library](https://www.chaijs.com), with all the confirmed invariants implemented as assertions. (this script can be accessed by clicking on the “Scripts” tab). Every time this script is executed, Postman generates a report such as the one shown in the bottom menu, in which it indicates whether each assertion passed or failed, with an explanatory message in case of failure. The collection reports can be exported in JSON format for further analysis.

# Publication

If you want to cite PostmanAssertify or any of the components of AGORA in your research, please use the BibTeX entry below.

```
@inproceedings{Alonso2023AGORA,
author = {Alonso, Juan C. and Segura, Sergio and Ruiz-Cort\'{e}s, Antonio},
title = {AGORA: Automated Generation of Test Oracles for REST APIs},
year = {2023},
isbn = {9798400702211},
publisher = {Association for Computing Machinery},
address = {New York, NY, USA},
url = {https://doi.org/10.1145/3597926.3598114},
doi = {10.1145/3597926.3598114},
abstract = {Test case generation tools for REST APIs have grown in number and complexity in recent years. However, their advanced capabilities for automated input generation contrast with the simplicity of their test oracles, which limit the types of failures they can detect to crashes, regressions, and violations of the API specification or design best practices. In this paper, we present AGORA, an approach for the automated generation of test oracles for REST APIs through the detection of invariants—properties of the output that should always hold. In practice, AGORA aims to learn the expected behavior of an API by analyzing previous API requests and their corresponding responses. For this, we extended the Daikon tool for dynamic detection of likely invariants, including the definition of new types of invariants and the implementation of an instrumenter called Beet. Beet converts any OpenAPI specification and a collection of API requests and responses to a format processable by Daikon. As a result, AGORA currently supports the detection of up to 105 different types of invariants in REST APIs. AGORA achieved a total precision of 81.2\% when tested on a dataset of 11 operations from 7 industrial APIs. More importantly, the test oracles generated by AGORA detected 6 out of every 10 errors systematically seeded in the outputs of the APIs under test. Additionally, AGORA revealed 11 bugs in APIs with millions of users: Amadeus, GitHub, Marvel, OMDb and YouTube. Our reports have guided developers in improving their APIs, including bug fixes and documentation updates in GitHub. Since it operates in black-box mode, AGORA can be seamlessly integrated into existing API testing tools.},
booktitle = {Proceedings of the 32nd ACM SIGSOFT International Symposium on Software Testing and Analysis},
pages = {1018–1030},
numpages = {13},
keywords = {automated testing, invariant detection, REST APIs, test oracle},
location = {Seattle, WA, USA},
series = {ISSTA 2023}
}
```

# Input CSV format

This section describes the columns that every row of the CSV file used as input for PostmanAssertify must contain. Please refer to the `src/main/resources/example/invariants_example.csv` file to see an example.

This CSV file is not expected to be generated manually; it should be generated by [*AGORA*](https://github.com/isa-group/Beet). Please, take into account that the `postmanAssertion` column is only implemented for the 106 Daikon invariants supported by AGORA.

The CSV file must have a header with the column names, use “;” as separator and have the same columns as the one returned by AGORA (plus the `tp` column). More specifically, it must contain the following columns in no specific order (additional columns will be ignored):
- `pptname`: Name of the program point in which the invariant was detected.
- `invariant`: Invariant in human-readable format.
- `invariantType`: Class of the Daikon invariant.
- `variables`: Variables involved in the invariant.
- `postmanAssertion`: Assertion implemented in the format of the Chai library.
- `tp`: Numerical flag (its value can be either “1” or “0”). The assertion will be implemented only if the value of this flag is “1” and ignored otherwise. This is the only new column with respect to the CSV returned by AGORA.
