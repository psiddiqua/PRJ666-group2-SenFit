'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Exercise extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */

    static associate(models) {
      // define association here
    }

  };
  Exercise.init({
    exerciseName: DataTypes.STRING,
    exerciseDescription: DataTypes.STRING(1024)
  }, {
    sequelize,
    modelName: 'Exercise',
  });
  return Exercise;
};