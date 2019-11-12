# springboot2
Endpoints для постмана
http://localhost:1111/actuator/health  актуатор
http://localhost:1111/app/employees       get - показывает всех employee

http://localhost:1111/app/employees/104    get - показывает определенного employee

http://localhost:1111/app/employees/105   delete -удаляет определенного employee

http://localhost:1111/app/employees/101   put -обновляет определенного employee
headers=  Content-Type   application/json

{
        "id": 101,
        "firstName": "Ivandffdfgdgf",
        "lastName": "Ivanov",
        "emailId": "qw@gmail.com"
    }


http://localhost:1111/app/employees/  post - добавляет определенного employee
headers=  Content-Type   application/json

{
        "id": 106,
        "firstName": "Ivan",
        "lastName": "Ivanov",
        "emailId": "qw@gmail.com"
    }
