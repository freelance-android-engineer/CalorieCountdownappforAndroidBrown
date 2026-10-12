package ese.com.caloriecountdownappforandroidbrown

class SurplusAccountType094Noir(val _inAccountValue: Double) : SurplusAccountType094
{
    override var accountValue: Double = _inAccountValue
        get() = field
        set(value)
        {
            field = value
        }




    override fun creditAccount(amount: Int): Boolean {
        if (amount < 0) return false
        //Algorithm Engineering -> ANDROID
        //Step One:
        //the purpsose of this fun is to add the integer amount inputed as an argument to
        //the Balance of this SurplusPastXEROAccount.

        accountValue += amount
        return true
    }




    override fun debitAccount(amount: Int): Boolean {
        if (amount < 0) return false
        //Algorithm Engineering -> ANDROID
        //Step One:
        //the purpsose of this fun is to subtract the integer amount inputed as an argument from
        //the Balance of this SurplusPastXEROAccount.🧪Cirlce

        accountValue -= amount
        return true
    }




    override fun emptyAccount(): Boolean {
        //Algorithm Engineering -> ANDROID
        //Step One:
        //the purpsose of this fun is to empty this account to zero Cr this SurplusPastXEROAccount.🧪Cirlce

        accountValue = 0.0
        return true

    }



}