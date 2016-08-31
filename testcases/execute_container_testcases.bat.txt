@echo on

D:


cd D:\selenium_plain_tests\selenium_testsuites

java -jar -Dwebdriver.chrome.driver='D:\SVN_Automation\software\chromedriver.exe' selenium-server-standalone-2.42.2.jar -htmlSuite "*googlechrome" "http://172.31.14.226:8081/CounterWebApp/" ".\selenium_functional_test_suite_container.xhtml" ".\container_results\selenium_container_results.html"

