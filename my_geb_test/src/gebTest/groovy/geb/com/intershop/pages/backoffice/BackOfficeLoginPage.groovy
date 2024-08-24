package geb.com.intershop.pages.backoffice

import geb.Page
import geb.com.intershop.model.intershop7.User

class BackOfficeLoginPage extends Page
{
    static url = "/INTERSHOP/web/WFS/SLDSystem"
    static at =
    {
        refreshWaitFor(180, 5) { $("div", "data-testing-id":"page-bo-login") }
    }

    static content =
    {
        loginInput        { $("input", id: "LoginForm_Login") }
        passwordInput     { $("input", id: "LoginForm_Password") }
        organizationInput { $("input", id: "selected_organization") }
        nextButton        { $("input", id: "btn_next") }
        loginButton(to: [BackOfficePage, BackOfficeLoginPage]) { $("input", "data-testing-id":"btn-login") }
    }
    
    def loginUser(user,password, organization)
    {
        organizationInput.value organization
        nextButton.click()
        loginInput.value user
        passwordInput.value password
        loginButton.click()

    }

    BackOfficePage login(User user, organization)
    {
        loginUser(user.email, user.password, organization)
        browser.page
    }   
}