class Animal  { 
  void sound() { 
    System.out.println("Animal 努贰胶狼 sound()"); 
  } 
} 
 
class Dog extends Animal { 
  void sound() { 
    System.out.println("港港"); 
  } 
} 
 
class Cat extends Animal { 
  void sound() { 
    System.out.println("具克"); 
  } 
} 
 
public class DynamicCallTest { 
  public static void main(String args[]) { 
    Animal animal = new Animal(); 
    Dog dog = new Dog(); 
    Cat cat = new Cat(); 

 
    Animal obj;  
 
    obj = animal;  
	obj.sound(); 
 
    obj = dog;  
	obj.sound(); 
 
    obj = cat;  
	obj.sound(); 
  } 
}