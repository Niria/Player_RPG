public class Punisher implements PlayerManager{
    private float attackNum = 0;
    private float maxAttackNum = 3;
    private String stateName = "Punisher";
    private String description = 
        "Il Talismano si rompe e ti dona la vita per altri tre attacchi. " +
        "Attacco triplicato e resistenza dimezzata. VENDICATI";

    @Override
    public void equipSpell(int position){
        Player.getInstance().equipSpellInInventory(position);
        Player.getInstance().updateAttackAmount();
        Player.getInstance().updateEquippedSpellName();
    }

    @Override
    public String getStateName() {
        return stateName;
    }

    @Override
    public String getDescription(){
        return description;
    }

    @Override
    public void onEnterState(){
        float maxHealth = Player.getInstance().getMaxHealth();
        Player.getInstance().setCurrentHealth(maxHealth/3);
        Player.getInstance().setResistenceMultiplyer(0.5f);
        Player.getInstance().setAttackMultiplyer(3);
        return;
    }

    @Override
    public void takeDamage(float amount) {
        float totalResistence = Player.getInstance().getCurrentResistence() *
            Player.getInstance().getResistenceMultiplyer();
        float reducedHealth = 
            Player.getInstance().getCurrentHealth() - 
            amount + totalResistence;
        System.out.println("A Valoroso vengono inflitti " + amount + " danni");
        System.out.println("Ne subisce " + (amount - totalResistence));
        if(reducedHealth <= 0){
            kill();
        }
        else{
            Player.getInstance().setCurrentHealth(reducedHealth);
        }
        return;
    }

    @Override
    public void heal(float amount) {
        System.out.println("Valoroso si cura di: " + amount + " punti");
        float increasedHealth = Player.getInstance().getCurrentHealth() + amount;
        float maxHealth = Player.getInstance().getMaxHealth();
        if(increasedHealth > maxHealth)
            Player.getInstance().setCurrentHealth(maxHealth);
        else
            Player.getInstance().setCurrentHealth(increasedHealth);
        return;
    }

    @Override
    public void attack() {
        
        System.out.println(
            "Valoroso punisce con: " + Player.getInstance().getEquippedSpellName()  +
            "\nInfligge: " + (Player.getInstance().getCurrentAttack() * Player.getInstance().getAttackMultiplyer()) + " danni."
        );
        attackNum++;

        if(attackNum == maxAttackNum)
            kill();
    
        return;
    }

    @Override
    public void kill() {
        Player.getInstance().setCurrentHealth(0);
        Player.getInstance().setMode(new Dead());
        return;
    }
    
}